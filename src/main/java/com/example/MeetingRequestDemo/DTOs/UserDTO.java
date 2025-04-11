package com.example.MeetingRequestDemo.DTOs;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String userEmail;
    private String userName;
    private String role;
    private String token;
}
