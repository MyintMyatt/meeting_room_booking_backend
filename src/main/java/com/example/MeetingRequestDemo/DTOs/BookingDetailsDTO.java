package com.example.MeetingRequestDemo.DTOs;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDetailsDTO {

    private String bookingID;

    private String meetingTitle;

    private String roomName;

    private String requesterName;

    private String requestType;

    private String department;

    private String requestDesc;

    private LocalDate requestDate;

    private String startTime;

    private String endTime;

    private String status;

    private String HOD;

    private LocalDateTime submittedDateTime;

    private LocalDateTime HODActionDateTime;

    private String HODcomment;

    private String admin;

    private LocalDateTime adminActionDateTime;

    private String adminComment;

}
