package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.Model.Booking;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;

@Service
public class FileService {

    public String readFileContentFromSources(Booking booking,  String hodName) {
        try (InputStream inputStream = getClass().getResource("/templates/emailTemplate.html").openStream()) {
            if (inputStream == null) {
                throw new RuntimeException("File not found.");
            }
            String htmlTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return replaceEmailFileContent(htmlTemplate, booking, hodName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String replaceEmailFileContent(String htmlTemplate, Booking booking ,String hodName) {
        htmlTemplate = htmlTemplate.replace("${name}", hodName);
        htmlTemplate = htmlTemplate.replace("${requester}", booking.getRequesterName());
        htmlTemplate = htmlTemplate.replace("${bookingID}",booking.getBookingID());
        htmlTemplate = htmlTemplate.replace("${meetingTitle}", booking.getMeetingTitle());
        htmlTemplate = htmlTemplate.replace("${roomName}", booking.getRoomName());
        htmlTemplate = htmlTemplate.replace("${requestType}", booking.getRequestType());
        htmlTemplate = htmlTemplate.replace("${department}", booking.getDepartment());
        htmlTemplate = htmlTemplate.replace("${reqDate}", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(booking.getRequestDate()));
        htmlTemplate = htmlTemplate.replace("${reqDesc}", booking.getRequestDesc());
        htmlTemplate = htmlTemplate.replace("${startTime}", booking.getStartTime());
        htmlTemplate = htmlTemplate.replace("${endTime}", booking.getEndTime());

        return htmlTemplate;
    }
}
