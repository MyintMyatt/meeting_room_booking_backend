package com.example.MeetingRequestDemo.Model;

import com.example.MeetingRequestDemo.Enum.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tblRoomBooking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking {

    @Id
    @GeneratedValue(generator = "booking-id-generator")
    @GenericGenerator(name = "booking-id-generator", strategy = "com.example.MeetingRequestDemo.Util.BookingIdGenerator")
    @Column(name = "bookingID", insertable = false, updatable = false, unique = true, length = 50)
    private String bookingID;

    @Column(name = "meetingTitle",nullable = false)
    private String meetingTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomID",referencedColumnName = "roomID", nullable = false)
    private MeetingRoom meetingRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requesterID", referencedColumnName = "userID", nullable = false)
    private Users requester;

    @Column(name = "requestType",nullable = false, length = 60)
    private String requestType;

    @Column(name = "department",nullable = false, length = 70)
    private String department;

    @Column(name = "requestDesc",nullable = false, columnDefinition = "varchar(max)")
    private String requestDesc;

    @Column(name = "requestDate", nullable = false)
    private LocalDate requestDate;

    @Column(name = "startTime",nullable = false, length = 40)
    private String startTime;

    @Column(name = "endTime",nullable = false, length = 40)
    private String endTime;

    @Column(name = "submittedDateTime")
    private LocalDateTime submittedDateTime;

    @Column(name = "status", length = 100)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOD", referencedColumnName = "userID")
    private Users HOD;

    @Column(name = "HODActionDateTime")
    private LocalDateTime HODActionDateTime;

    @Column(name = "HODcomment", columnDefinition = "varchar(max)")
    private String HODcomment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin", referencedColumnName = "userID")
    private Users admin;

    @Column(name = "adminActionDateTime")
    private LocalDateTime adminActionDateTime;

    @Column(name = "adminComment", columnDefinition = "nvarchar(max)")
    private String adminComment;

    @PrePersist
    protected void setBookingDate() {
        this.submittedDateTime = LocalDateTime.now();
        this.status = String.valueOf(BookingStatus.PENDING);
    }

}