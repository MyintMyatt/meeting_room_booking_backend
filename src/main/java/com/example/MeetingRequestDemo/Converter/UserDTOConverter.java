package com.example.MeetingRequestDemo.Converter;

import com.example.MeetingRequestDemo.DTOs.UserDTO;
import com.example.MeetingRequestDemo.Model.Users;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO userToUserDTO(Users users) {
        return modelMapper.map(users, UserDTO.class);
    }
}
