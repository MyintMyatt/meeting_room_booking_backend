package com.example.MeetingRequestDemo.Repository;

import com.example.MeetingRequestDemo.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users, Integer> {

    Users findByUserEmail(String userEmail);

}
