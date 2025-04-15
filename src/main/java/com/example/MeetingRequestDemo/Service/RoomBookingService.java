package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.Config.AppStartupRunner;
import com.example.MeetingRequestDemo.Converter.BookingDTOConverter;
import com.example.MeetingRequestDemo.DTOs.AdminBookingActionDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDetailsDTO;
import com.example.MeetingRequestDemo.DTOs.HODbookingActionDTO;
import com.example.MeetingRequestDemo.Enum.BookingStatus;
import com.example.MeetingRequestDemo.Enum.UserRoles;
import com.example.MeetingRequestDemo.Model.Booking;
import com.example.MeetingRequestDemo.Model.MeetingRoom;
import com.example.MeetingRequestDemo.Model.Users;
import com.example.MeetingRequestDemo.Repository.MeetingRoomRepo;
import com.example.MeetingRequestDemo.Repository.RoomBookingRepo;
import com.example.MeetingRequestDemo.Repository.UsersRepo;
import com.example.MeetingRequestDemo.Util.BookingTimeVaildator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomBookingService {

    @Autowired
    private RoomBookingRepo roomBookingRepo;

    @Autowired
    private BookingDTOConverter bookingDTOConverter;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MeetingRoomRepo meetingRoomRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private AppStartupRunner appStartupRunner;

    public List<BookingDetailsDTO> getAllBooking() {
        List<Booking> bookingList = roomBookingRepo.findAll();
        return bookingList.stream().map(bookingDTOConverter::bookingToBookingDetailsDTO).collect(Collectors.toList());
    }

    public Optional<BookingDetailsDTO> findBooking(String bookingID) {
        Optional<Booking> optionalBooking = roomBookingRepo.findByBookingID(bookingID);
        return optionalBooking.map(bookingDTOConverter::bookingToBookingDetailsDTO);
    }

    /**
     * @Transactional ensures that the method runs within a transactional context.
     * This is required for database operations like flush() and refresh() to work properly,
     * as they rely on the transaction management provided by Spring.
     * Without @Transactional, Hibernate cannot guarantee the reliability of operations like
     * persisting data and fetching computed columns, which can lead to errors such as missing (null)
     * or null values for computed fields like 'bookingID'.
     */
    @Transactional
    public ResponseEntity<Map<String,String>> bookRoom(BookingDTO bookingDTO) {
        MeetingRoom room = meetingRoomRepo.findByRoomID(bookingDTO.getMeetingRoom().getRoomID());
        bookingDTO.setMeetingRoom(room);
        Users requester = usersRepo.findByUserID(bookingDTO.getRequester().getUserID());
        bookingDTO.setRequester(requester);

        //check room status (booked or not booked)
        List<Booking> existingBookings = roomBookingRepo.findByMeetingRoomRoomIDAndRequestDate(bookingDTO.getMeetingRoom().getRoomID(), bookingDTO.getRequestDate());
        boolean isBooked = BookingTimeVaildator.isTimeBooked(existingBookings, bookingDTO.getStartTime(), bookingDTO.getEndTime());
        if (isBooked) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("result",String.format("%s is already booked for %s - %s.", bookingDTO.getMeetingRoom().getRoomName(), bookingDTO.getStartTime(), bookingDTO.getEndTime())));
        }


        //change booking to booking dto
        Booking booking = bookingDTOConverter.bookingDTOtoBooking(bookingDTO);
        //Save the booking in the database, but when returning the data, exclude any computed columns to prevent errors when replacing HTML content in the email service
        booking = roomBookingRepo.save(booking);
       /* // Force flush to DB to ensure insert operation is complete
        entityManager.flush();
        // Refresh the entity to retrieve the computed bookingID
        entityManager.refresh(booking);
        // Fetch again to get computed column
        Booking b = roomBookingRepo.findByBookingID(booking.getBookingID()).orElse(null);
        //send mail to relevant department hod*/
        emailService.sendMail(booking, UserRoles.HOD, null);
        return ResponseEntity.ok(Map.of("result",String.format("%s successfully booked for %s - %s.", bookingDTO.getMeetingRoom().getRoomName(), bookingDTO.getStartTime(), bookingDTO.getEndTime())));
    }

    public Map<String, Object> responseJSON() {
        Map<String, Object> response = new HashMap<>();
        return response;
    }

    public Map<String, Object> actsByHOD(String bookingID, HODbookingActionDTO hoDbookingActionDTO) {
        LocalDateTime now = LocalDateTime.now();
        String status = hoDbookingActionDTO.getStatus().equals(String.valueOf(BookingStatus.APPROVED_BY_HOD)) ? String.valueOf(BookingStatus.APPROVED_BY_HOD) : String.valueOf(BookingStatus.REJECTED_BY_HOD);
        //get approver details
        Users hod = appStartupRunner.getRelevantApproverDetails(hoDbookingActionDTO.getHOD()); //getHOD() returns hod email
        if (hod != null) {
            roomBookingRepo.updateHODDetails(bookingID, status, hod, now, hoDbookingActionDTO.getHODcomment());
            System.err.println("Successfully updated for booking id " + bookingID + ": status = " + hoDbookingActionDTO.getStatus());
            // Make sure next findByBookingID fetches fresh data
            entityManager.clear();
            //for email body content
            Booking booking = roomBookingRepo.findByBookingID(bookingID).orElse(null);
            if (hoDbookingActionDTO.getStatus().toUpperCase().equals(String.valueOf(BookingStatus.APPROVED_BY_HOD))) {
                //Approve => send mail to Admin
                emailService.sendMail(booking, UserRoles.ADMIN, hod);
            } else {
                //Reject => send mail to requester
                emailService.sendMail(booking, UserRoles.USER, hod);
            }
            Map<String, Object> response = responseJSON();
            response.put("status", "Successfully updated");
            return response;
        }
        return Map.of("status", "failed");

    }

    public Map<String, Object> actsByAdmin(String bookingID, AdminBookingActionDTO adminBookingActionDTO) {
        LocalDateTime now = LocalDateTime.now();
        String status = adminBookingActionDTO.getStatus().equals(String.valueOf(BookingStatus.APPROVED_BY_ADMIN)) ? String.valueOf(BookingStatus.APPROVED_BY_ADMIN) : String.valueOf(BookingStatus.REJECTED_BY_ADMIN);
        //get approver details
        Users admin = appStartupRunner.getRelevantApproverDetails(adminBookingActionDTO.getAdmin());// getAdmin() returns admin email
        roomBookingRepo.updateAdminDetails(bookingID, status, admin, now, adminBookingActionDTO.getAdminComment());
        System.err.println("Successfully updated for booking id " + bookingID);
        // Make sure next findByBookingID fetches fresh data
        entityManager.clear();
        Booking booking = roomBookingRepo.findByBookingID(bookingID).orElse(null);
        if (booking != null) {
            //send mail to requester
            emailService.sendMail(booking, UserRoles.USER, admin);
        }
        Map<String, Object> response = responseJSON();
        response.put("status", "Successfully updated");
        return response;
    }
}
