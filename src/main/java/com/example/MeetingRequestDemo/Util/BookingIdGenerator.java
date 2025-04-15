package com.example.MeetingRequestDemo.Util;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.time.LocalDateTime;

public class BookingIdGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object o) {
        LocalDateTime now = LocalDateTime.now();
        String yy = String.format("%02d", now.getYear() % 100); // 2025 => 25
        String mm = String.format("%02d", now.getMonthValue()); //May => 05
        String prefix = "B" + yy + mm;

        String query = "SELECT COUNT (*) FROM tblRoomBooking";

        //serial number is always start from 1 for every month
        //String query = "SELECT TOP 1 bookingID FROM tblRoomBooking WHERE bookingID LIKE '" + prefix + "%' ORDER BY bookingID DESC";

        Object lastId = session.createNativeQuery(query).getSingleResult();
        if (lastId != null) {
            int nextID = ((Number) lastId).intValue() + 1; // plus one for the next id
            return prefix + String.format("%04d", nextID);
        }

        return prefix+ "0001";//for the first time
    }
}
