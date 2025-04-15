package com.example.MeetingRequestDemo.Util;

import com.example.MeetingRequestDemo.Model.Booking;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingTimeVaildator {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    public static boolean isTimeBooked(List<Booking> existingBookings, String start, String end) {
        LocalTime newStartTime = LocalTime.parse(start, timeFormatter);
        LocalTime newEndTime = LocalTime.parse(end, timeFormatter);

        for (Booking booking : existingBookings) {
            LocalTime exitingStartTime = LocalTime.parse(booking.getStartTime(), timeFormatter);
            LocalTime exitingEndTime = LocalTime.parse(booking.getEndTime(), timeFormatter);

            //check room is booked or not
            if (newStartTime.isBefore(exitingEndTime) && newEndTime.isAfter(exitingStartTime))
                return true;// room is booked at that time
        }
        return false;// room is not booked at that time
    }
}
