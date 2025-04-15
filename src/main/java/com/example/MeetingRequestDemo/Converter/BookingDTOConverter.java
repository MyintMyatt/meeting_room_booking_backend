package com.example.MeetingRequestDemo.Converter;

import com.example.MeetingRequestDemo.DTOs.BookingDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDetailsDTO;
import com.example.MeetingRequestDemo.DTOs.HODbookingActionDTO;
import com.example.MeetingRequestDemo.Model.Booking;
import com.example.MeetingRequestDemo.Model.Users;
import com.example.MeetingRequestDemo.Repository.UsersRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsersRepo usersRepo;

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
        BookingDetailsDTO detailsDTO = modelMapper.map(booking, BookingDetailsDTO.class);
        if (booking.getHOD() != null)
            detailsDTO.setHOD(booking.getHOD().getUserEmail());
        if (booking.getAdmin() != null)
            detailsDTO.setAdmin(booking.getAdmin().getUserEmail());
        return detailsDTO;
    }
}
