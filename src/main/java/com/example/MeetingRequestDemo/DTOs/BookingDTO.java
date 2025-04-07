package com.example.MeetingRequestDemo.DTOs;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDTO {

    private String meetingTitle;

    private String roomName;

    private String requesterName;

    private String requestType;

    private String department;

    private  String requestDesc;

    private LocalDate requestDate;

    private String startTime;

    private String endTime;


}
