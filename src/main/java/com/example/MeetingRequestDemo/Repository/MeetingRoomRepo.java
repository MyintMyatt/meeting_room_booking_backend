package com.example.MeetingRequestDemo.Repository;

import com.example.MeetingRequestDemo.Model.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRoomRepo extends JpaRepository<MeetingRoom, Integer>{
    MeetingRoom findByRoomID(String roomID);
}
