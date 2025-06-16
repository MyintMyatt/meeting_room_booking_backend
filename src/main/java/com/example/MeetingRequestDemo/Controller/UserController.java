package com.example.MeetingRequestDemo.Controller;

import com.example.MeetingRequestDemo.DTOs.UserDTO;
import com.example.MeetingRequestDemo.Model.Users;
import com.example.MeetingRequestDemo.Service.EmailService;
import com.example.MeetingRequestDemo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cp")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        Map<String, Object> response = new HashMap<>();
        try {
//            emailService.sendMail();
            String token = userService.verify(user);
            UserDTO userDTO = userService.getUsers(user.getUserEmail());
            userDTO.setToken(token);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            response.put("result", "Invalid username or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.registerUser(user);
    }

   // @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/allUsers")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
