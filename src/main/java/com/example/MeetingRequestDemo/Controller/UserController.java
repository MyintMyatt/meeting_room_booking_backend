package com.example.MeetingRequestDemo.Controller;

import com.example.MeetingRequestDemo.Model.Users;
import com.example.MeetingRequestDemo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cp")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return userService.verify(user);
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.registerUser(user);
    }

    @GetMapping("/allUsers")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }
}
