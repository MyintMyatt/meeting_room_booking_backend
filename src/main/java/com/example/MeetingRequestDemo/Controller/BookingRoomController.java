package com.example.MeetingRequestDemo.Controller;

import com.example.MeetingRequestDemo.DTOs.BookingDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDetailsDTO;
import com.example.MeetingRequestDemo.DTOs.HODbookingActionDTO;
import com.example.MeetingRequestDemo.Repository.RoomBookingRepo;
import com.example.MeetingRequestDemo.Service.RoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cp")
public class BookingRoomController {

    @Autowired
    private RoomBookingService roomBookingService;

    @Autowired
    private RoomBookingRepo roomBookingRepo;

    @GetMapping("/searchBooking/{bookingID}")
    public ResponseEntity<BookingDetailsDTO> findBooking(@PathVariable String bookingID) {
       Optional<BookingDetailsDTO> optionalBookingDTO = roomBookingService.findBooking(bookingID);
        return optionalBookingDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/newBooking")
    public ResponseEntity<BookingDTO> newBooking(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(roomBookingService.bookRoom(bookingDTO));
    }

    @PutMapping("/hod/{bookingID}")
    public ResponseEntity<HODbookingActionDTO> actsByHOD(@RequestParam String bookingID, HODbookingActionDTO hoDbookingActionDTO) {
        if (roomBookingRepo.findByBookingID(bookingID).isPresent()) {
            return ResponseEntity.ok(roomBookingService.actsByHOD(hoDbookingActionDTO));
        }else
            return ResponseEntity.notFound().build();
    }

}
