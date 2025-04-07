package com.example.MeetingRequestDemo.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeetingRoomDTO {

    private String roomID;
    private String name;
    private int roomCapacity;
    private String status;

}
