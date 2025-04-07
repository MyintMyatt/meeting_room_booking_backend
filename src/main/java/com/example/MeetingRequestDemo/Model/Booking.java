package com.example.MeetingRequestDemo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tblRoomBooking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sysKey")
    private Integer sysKey;

    @Column(name = "bookingID", insertable = false, updatable = false, unique = true)
    private String bookingID;

    @Column(name = "meetingTitle",nullable = false)
    private String meetingTitle;

    @Column(name = "roomName", nullable = false, length = 100)
    private String roomName;

    @Column(name = "requesterName", length = 100)
    private String requesterName;

    @Column(name = "requestType", length = 60)
    private String requestType;

    @Column(name = "department", length = 70)
    private String department;

    @Column(name = "requestDesc", columnDefinition = "nvarchar(max)")
    private String requestDesc;

    @Column(name = "requestDate")
    private LocalDate requestDate;

    @Column(name = "startTime", length = 40)
    private String startTime;

    @Column(name = "endTime", length = 40)
    private String endTime;

    @Column(name = "submittedDateTime")
    private LocalDateTime submittedDateTime;

    @Column(name = "status", length = 100)
    private String status;

    @Column(name = "HOD", length = 100)
    private String HOD;

    @Column(name = "HODActionDateTime")
    private LocalDateTime HODActionDateTime;

    @Column(name = "HODcomment", columnDefinition = "nvarchar(max)")
    private String HODcomment;

    @Column(name = "admin", length = 100)
    private String admin;

    @Column(name = "adminActionDateTime")
    private LocalDateTime adminActionDateTime;

    @Column(name = "adminComment", columnDefinition = "nvarchar(max)")
    private String adminComment;

    @PrePersist
    protected void setBookingDate() {
        this.submittedDateTime = LocalDateTime.now();
        this.status = "Pending";
    }

    @PreUpdate
    protected void onUpdate() {
        if (this.status == "Approved" || this.status == "Rejected") {
            if (this.HOD != null) {  // If HOD is making the update
                this.HODActionDateTime = LocalDateTime.now();
            }
            if (this.admin != null) { // If Admin is making the update
                this.adminActionDateTime = LocalDateTime.now();
            }
        }
    }


}
