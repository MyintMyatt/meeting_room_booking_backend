package com.example.MeetingRequestDemo.Controller;

import com.example.MeetingRequestDemo.DTOs.MeetingRoomDTO;
import com.example.MeetingRequestDemo.Service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cp")
public class MeetingRoomController {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @GetMapping("/meetingRoom")
    private List<MeetingRoomDTO> getAllMeetingRoom() {
        for (MeetingRoomDTO room : meetingRoomService.getAllMeetingRoom()) {
            System.err.println(room.getName());
        }
        return meetingRoomService.getAllMeetingRoom();
    }

}
