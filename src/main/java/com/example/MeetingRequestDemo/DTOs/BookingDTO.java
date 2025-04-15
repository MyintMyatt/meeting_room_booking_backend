package com.example.MeetingRequestDemo.DTOs;

import com.example.MeetingRequestDemo.Model.MeetingRoom;
import com.example.MeetingRequestDemo.Model.Users;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDTO {

    private String meetingTitle;

    private MeetingRoom meetingRoom;

    private Users requester;

    private String requestType;

    private String department;

    private  String requestDesc;

    private LocalDate requestDate;

    private String startTime;

    private String endTime;


}
