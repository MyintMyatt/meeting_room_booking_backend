package com.example.MeetingRequestDemo.Repository;

import com.example.MeetingRequestDemo.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomBookingRepo extends JpaRepository<Booking, Integer> {
    Optional<Booking> findByBookingID(String bookingID);
}
