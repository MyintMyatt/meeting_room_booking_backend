package com.example.MeetingRequestDemo.Config;

import com.example.MeetingRequestDemo.Model.Users;
import com.example.MeetingRequestDemo.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppStartupRunner implements CommandLineRunner {

    @Autowired
    private UsersRepo usersRepository;

    private List<Users> approverList = new ArrayList<>();

    public Users getRelevantApproverDetails(String email) {
        for (Users approver : approverList) {
            if (approver.getUserEmail().equals(email)) {
                return approver;
            }
        }
        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        approverList = usersRepository.findByRoleNot("USER");
    }


}

