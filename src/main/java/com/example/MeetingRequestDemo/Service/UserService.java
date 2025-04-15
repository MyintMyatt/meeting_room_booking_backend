package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.Converter.UserDTOConverter;
import com.example.MeetingRequestDemo.DTOs.UserDTO;
import com.example.MeetingRequestDemo.Model.Users;
import com.example.MeetingRequestDemo.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserDTOConverter userDTOConverter;

    public Users registerUser(Users users) {
        System.err.println(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now()) + " : New User has been Registered => " + users);
        users.setRole(users.getRole().trim().toUpperCase());
        users.setPassword(encoder.encode(users.getPassword()));
        return usersRepo.save(users);
    }

    public List<UserDTO> getAllUsers() {
        return usersRepo.findAll().stream().map(userDTOConverter::userToUserDTO).collect(Collectors.toList());
    }

    public UserDTO getUsers(String userEmail) {
        Users users = usersRepo.findByUserEmail(userEmail);
        return userDTOConverter.userToUserDTO(users);
    }

    public Users getApprover(String department, String role) {
        Users user = usersRepo.findByDepartmentAndRole(department, role);
        return user;
    }

    public String verify(Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            System.err.println(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now()) + " : Login Success => " + user);
            return jwtService.generateToken(user);
        } else {
            System.err.println(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now()) + " : Login Fail => " + user);
            return "fails";
        }
    }
}
