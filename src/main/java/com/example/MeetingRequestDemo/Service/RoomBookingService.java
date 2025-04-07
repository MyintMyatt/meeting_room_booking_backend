package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.Converter.BookingDTOConverter;
import com.example.MeetingRequestDemo.DTOs.BookingDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDetailsDTO;
import com.example.MeetingRequestDemo.DTOs.HODbookingActionDTO;
import com.example.MeetingRequestDemo.Model.Booking;
import com.example.MeetingRequestDemo.Repository.RoomBookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomBookingService {

    @Autowired
    private RoomBookingRepo roomBookingRepo;

    @Autowired
    private BookingDTOConverter bookingDTOConverter;

    public Optional<BookingDetailsDTO> findBooking(String bookingID) {
        Optional<Booking> optionalBooking = roomBookingRepo.findByBookingID(bookingID);
        return optionalBooking.map(bookingDTOConverter::bookingToBookingDetailsDTO);  // ✅
    }

    public BookingDTO bookRoom(BookingDTO bookingDTO) {
        Booking booking = bookingDTOConverter.bookingDTOtoBooking(bookingDTO);
        booking =  roomBookingRepo.save(booking);
        return bookingDTOConverter.bookingToBookingDTO(booking);
    }


    public HODbookingActionDTO actsByHOD(HODbookingActionDTO hoDbookingActionDTO) {
        Booking booking = bookingDTOConverter.HODbookingActionDTOtoBooking(hoDbookingActionDTO);
        booking = roomBookingRepo.save(booking);
        return bookingDTOConverter.bookingToHODbookingActionDTO(booking);
    }
}
