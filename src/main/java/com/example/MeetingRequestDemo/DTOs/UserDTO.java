package com.example.MeetingRequestDemo.DTOs;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userID;
    private String userEmail;
    private String userName;
    private String role;
    private String department;
    private String token;
}
