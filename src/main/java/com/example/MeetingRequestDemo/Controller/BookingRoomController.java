package com.example.MeetingRequestDemo.Controller;

import com.example.MeetingRequestDemo.DTOs.AdminBookingActionDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDTO;
import com.example.MeetingRequestDemo.DTOs.BookingDetailsDTO;
import com.example.MeetingRequestDemo.DTOs.HODbookingActionDTO;
import com.example.MeetingRequestDemo.Enum.BookingStatus;
import com.example.MeetingRequestDemo.Model.Booking;
import com.example.MeetingRequestDemo.Repository.RoomBookingRepo;
import com.example.MeetingRequestDemo.Service.RoomBookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cp")
public class BookingRoomController {

    @Autowired
    private RoomBookingService roomBookingService;

    @Autowired
    private RoomBookingRepo roomBookingRepo;


    @GetMapping("/allBookingLists")
    public ResponseEntity<List<BookingDetailsDTO>> getAllBookings(){
        return ResponseEntity.ok(roomBookingService.getAllBooking());
    }

    @CrossOrigin(origins = "*")
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
    public ResponseEntity<String> actsByHOD(@PathVariable String bookingID,@Valid @RequestBody HODbookingActionDTO hoDbookingActionDTO) {
        if (roomBookingRepo.findByBookingID(bookingID).isPresent()) {
            return ResponseEntity.ok(roomBookingService.actsByHOD(bookingID, hoDbookingActionDTO));
        }else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/admin/{bookingID}")
    public ResponseEntity<String> actsByAdmin(@PathVariable String bookingID, @Valid @RequestBody AdminBookingActionDTO adminBookingActionDTO) {
        Optional<Booking> booking = roomBookingRepo.findByBookingID(bookingID);
        System.err.println(booking.get().getStatus());
        if (booking.isPresent()) {
            if (BookingStatus.valueOf(booking.get().getStatus().toUpperCase()) == BookingStatus.APPROVED_BY_HOD){
                return ResponseEntity.ok(roomBookingService.actsByAdmin(bookingID, adminBookingActionDTO));
            }else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("HOD not approved yet for booking id = " + bookingID);
        }else return ResponseEntity.notFound().build();
    }

}
