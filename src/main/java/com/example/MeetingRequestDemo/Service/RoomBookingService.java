package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.Converter.BookingDTOConverter;
import com.example.MeetingRequestDemo.DTOs.AdminBookingActionDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDetailsDTO;
import com.example.MeetingRequestDemo.DTOs.HODbookingActionDTO;
import com.example.MeetingRequestDemo.Enum.BookingStatus;
import com.example.MeetingRequestDemo.Model.Booking;
import com.example.MeetingRequestDemo.Repository.RoomBookingRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<BookingDetailsDTO> getAllBooking() {
        List<Booking> bookingList = roomBookingRepo.findAll();
        return bookingList.stream().map(bookingDTOConverter::bookingToBookingDetailsDTO).collect(Collectors.toList());
    }

    public Optional<BookingDetailsDTO> findBooking(String bookingID) {
        Optional<Booking> optionalBooking = roomBookingRepo.findByBookingID(bookingID);
        return optionalBooking.map(bookingDTOConverter::bookingToBookingDetailsDTO);  // ✅
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
    public BookingDTO bookRoom(BookingDTO bookingDTO) {
        //change booking to booking dto
        Booking booking = bookingDTOConverter.bookingDTOtoBooking(bookingDTO);
        //Save the booking in the database, but when returning the data, exclude any computed columns to prevent errors when replacing HTML content in the email service
        booking =  roomBookingRepo.save(booking);
        // Force flush to DB to ensure insert operation is complete
        entityManager.flush();
        // Refresh the entity to retrieve the computed bookingID
        entityManager.refresh(booking);
        // Fetch again to get computed column
        Booking b = roomBookingRepo.findById(booking.getSysKey()).orElse(null);
        //send mail to relevant department hod
        emailService.sendMail(b);
        return bookingDTOConverter.bookingToBookingDTO(booking);
    }

    public Map<String, Object> responseJSON() {
        Map<String, Object> response = new HashMap<>();
        return response;
    }

    public Map<String,Object> actsByHOD(String bookingID, HODbookingActionDTO hoDbookingActionDTO) {
        LocalDateTime now = LocalDateTime.now();
        String status = hoDbookingActionDTO.getStatus().equals(String.valueOf(BookingStatus.APPROVED_BY_HOD)) ? String.valueOf(BookingStatus.APPROVED_BY_HOD) : String.valueOf(BookingStatus.REJECTED_BY_HOD);
        roomBookingRepo.updateHODDetails(bookingID,status ,hoDbookingActionDTO.getHOD(),now, hoDbookingActionDTO.getHODcomment());
        System.err.println("Successfully updated for booking id "+bookingID);
        Map<String, Object> response = responseJSON();
        response.put("status", "Successfully updated" );
        return response;

    }

    public Map<String, Object> actsByAdmin(String bookingID, AdminBookingActionDTO adminBookingActionDTO) {
        LocalDateTime now = LocalDateTime.now();
        String status = adminBookingActionDTO.getStatus().equals(String.valueOf(BookingStatus.APPROVED_BY_ADMIN)) ? String.valueOf(BookingStatus.APPROVED_BY_ADMIN) : String.valueOf(BookingStatus.REJECTED_BY_ADMIN);
        roomBookingRepo.updateAdminDetails(bookingID, status,adminBookingActionDTO.getAdmin(),now, adminBookingActionDTO.getAdminComment());
        System.err.println("Successfully updated for booking id "+bookingID);
        Map<String, Object> response = responseJSON();
        response.put("status", "Successfully updated" );
        return response;
    }
}
