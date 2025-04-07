package com.example.MeetingRequestDemo.Converter;

import com.example.MeetingRequestDemo.DTOs.BookingDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDetailsDTO;
import com.example.MeetingRequestDemo.DTOs.HODbookingActionDTO;
import com.example.MeetingRequestDemo.Model.Booking;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Booking bookingDTOtoBooking(BookingDTO bookingDTO) {
        return modelMapper.map(bookingDTO, Booking.class);
    }

    public BookingDTO bookingToBookingDTO(Booking booking) {
        return modelMapper.map(booking, BookingDTO.class);
    }

    // -------------------------------------------------------
    public Booking HODbookingActionDTOtoBooking(HODbookingActionDTO hoDbookingActionDTO) {
        return modelMapper.map(hoDbookingActionDTO, Booking.class);
    }


    public HODbookingActionDTO bookingToHODbookingActionDTO(Booking booking) {
        return modelMapper.map(booking, HODbookingActionDTO.class);
    }

    //-------------------------------------------------------
    public BookingDetailsDTO bookingToBookingDetailsDTO(Booking booking) {
        return modelMapper.map(booking, BookingDetailsDTO.class);
    }
}
