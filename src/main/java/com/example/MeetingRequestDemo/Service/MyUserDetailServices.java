package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.Model.UserPrinciple;
import com.example.MeetingRequestDemo.Model.Users;
import com.example.MeetingRequestDemo.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailServices implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Users user = usersRepo.findByUserEmail(userEmail);

        if (user == null) {
            System.err.println("User not found");
            throw new UsernameNotFoundException("User Not Found");
        }

        return new UserPrinciple(user);
    }
}
