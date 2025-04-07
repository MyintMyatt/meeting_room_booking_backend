package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.DTOs.MeetingRoomDTO;
import com.example.MeetingRequestDemo.Repository.MeetingRoomRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MeetingRoomService {

    @Autowired
    private MeetingRoomRepo meetingRoomRepo;

    public List<MeetingRoomDTO> getAllMeetingRoom() {
//        return  meetingRoomRepo.findAll();
        return meetingRoomRepo.findAll().stream().map(room -> new MeetingRoomDTO(room.getRoomID(),room.getName(),room.getRoomCapacity(),room.getStatus())).collect(Collectors.toList());
    }
}
