package com.example.MeetingRequestDemo.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblMeetingRoom")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sysKey")
    private Integer sysKey;

    @Column(name = "roomID", insertable = false, updatable = false, unique = true)
    private String roomID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "roomCapacity")
    private Integer roomCapacity;

    @Column(name = "status")
    private String status;

}
