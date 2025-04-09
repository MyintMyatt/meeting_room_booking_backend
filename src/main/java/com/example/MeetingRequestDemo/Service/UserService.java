package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.Model.Users;
import com.example.MeetingRequestDemo.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Users registerUser(Users users) {
        users.setPassword(encoder.encode(users.getPassword()));
        return usersRepo.save(users);
    }

    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    public String verify(Users user) {
        System.err.println(user);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user);
        }else
            return "fails";
    }
}
