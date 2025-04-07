package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.Converter.BookingDTOConverter;
import com.example.MeetingRequestDemo.DTOs.AdminBookingActionDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDetailsDTO;
import com.example.MeetingRequestDemo.DTOs.HODbookingActionDTO;
import com.example.MeetingRequestDemo.Enum.BookingStatus;
import com.example.MeetingRequestDemo.Model.Booking;
import com.example.MeetingRequestDemo.Repository.RoomBookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomBookingService {

    @Autowired
    private RoomBookingRepo roomBookingRepo;

    @Autowired
    private BookingDTOConverter bookingDTOConverter;

    public List<BookingDetailsDTO> getAllBooking() {
        List<Booking> bookingList = roomBookingRepo.findAll();
        return bookingList.stream().map(bookingDTOConverter::bookingToBookingDetailsDTO).collect(Collectors.toList());
    }

    public Optional<BookingDetailsDTO> findBooking(String bookingID) {
        Optional<Booking> optionalBooking = roomBookingRepo.findByBookingID(bookingID);
        return optionalBooking.map(bookingDTOConverter::bookingToBookingDetailsDTO);  // ✅
    }

    public BookingDTO bookRoom(BookingDTO bookingDTO) {
        Booking booking = bookingDTOConverter.bookingDTOtoBooking(bookingDTO);
        booking =  roomBookingRepo.save(booking);
        return bookingDTOConverter.bookingToBookingDTO(booking);
    }


    public String actsByHOD(String bookingID, HODbookingActionDTO hoDbookingActionDTO) {
        LocalDateTime now = LocalDateTime.now();
        String status = hoDbookingActionDTO.getStatus().toLowerCase().equals("approved") ? String.valueOf(BookingStatus.APPROVED_BY_HOD) : String.valueOf(BookingStatus.REJECTED_BY_HOD);
        roomBookingRepo.updateHODDetails(bookingID,status ,hoDbookingActionDTO.getHOD(),now, hoDbookingActionDTO.getHODcomment());
        System.err.println("Successfully updated for booking id "+bookingID);
        return "Successfully updated for booking id "+bookingID;
    }

    public String actsByAdmin(String bookingID, AdminBookingActionDTO adminBookingActionDTO) {
        LocalDateTime now = LocalDateTime.now();
        String status = adminBookingActionDTO.getStatus().toLowerCase().equals("approved") ? String.valueOf(BookingStatus.APPROVED_BY_ADMIN) : String.valueOf(BookingStatus.REJECTED_BY_ADMIN);
        roomBookingRepo.updateAdminDetails(bookingID, status,adminBookingActionDTO.getAdmin(),now, adminBookingActionDTO.getAdminComment());
        System.err.println("Successfully updated for booking id "+bookingID);
        return "Successfully updated for booking id "+bookingID;
    }
}
