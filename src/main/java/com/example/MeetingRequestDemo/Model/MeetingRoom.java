package com.example.MeetingRequestDemo.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tblMeetingRoom")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class MeetingRoom {

    @Id
    @Column(name = "roomID", insertable = false, updatable = false, unique = true, length = 10)
    private String roomID;

    @Column(name = "roomName", nullable = false)
    private String roomName;

    @Column(name = "roomCapacity")
    private Integer roomCapacity;

    @Column(name = "status")
    private String status;

}
