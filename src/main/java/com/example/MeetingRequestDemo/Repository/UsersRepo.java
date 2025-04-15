package com.example.MeetingRequestDemo.Repository;

import com.example.MeetingRequestDemo.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepo extends JpaRepository<Users, Integer> {

    Users findByUserEmail(String userEmail);

    Users findByDepartmentAndRole(String department, String role);

    Users findByUserID(String userID);

    List<Users> findByRoleNot(String user);
}
