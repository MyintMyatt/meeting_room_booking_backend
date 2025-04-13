package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.Enum.BookingStatus;
import com.example.MeetingRequestDemo.Enum.UserRoles;
import com.example.MeetingRequestDemo.Model.Booking;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Service
public class FileService {

    public String readFileContentFromSources(Booking booking, String receiverName, UserRoles sendTo) {
        try (InputStream inputStream = getClass().getResource("/templates/emailTemplate.html").openStream()) {
            if (inputStream == null) {
                throw new RuntimeException("File not found.");
            }
            String htmlTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return replaceEmailFileContent(htmlTemplate, booking, receiverName, sendTo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String replaceEmailFileContent(String htmlTemplate, Booking booking, String receiverName, UserRoles sendTo) {
        htmlTemplate = htmlTemplate.replace("${name}", receiverName);
        switch (sendTo) {
            case HOD: {
                htmlTemplate = htmlTemplate.replace("${header}", booking.getRequesterName() + " requested a meeting room. Details are as follows:");
                htmlTemplate = htmlTemplate.replace("${approverAction}", "");
                htmlTemplate = htmlTemplate.replace("${footer}", "Please approve or reject the request");
                break;
            }
            case ADMIN: {
                htmlTemplate = htmlTemplate.replace("${header}", booking.getRequesterName() + " requested a meeting room. Details are as follows:");
                htmlTemplate = htmlTemplate.replace("${approverAction}", booking.getHOD() + "'s comment is " + booking.getHODcomment());
                htmlTemplate = htmlTemplate.replace("${footer}", "Please approve or reject the request");
                break;
            }
            case USER: {
                String status = booking.getStatus().toUpperCase().equals(BookingStatus.APPROVED_BY_ADMIN.name()) ? "approved" : "rejected";
                String admin = booking.getStatus().toUpperCase().equals(BookingStatus.REJECTED_BY_HOD.name()) ? "" : booking.getAdmin() + "'s comment is " + booking.getAdminComment();
                htmlTemplate = htmlTemplate.replace("${header}", "Your request for meeting room have been " + status + ".Details are as follows:");
                htmlTemplate = htmlTemplate.replace("${approverAction}", booking.getHOD() + "'s comment is " + booking.getHODcomment() + "</br>" + admin);
                htmlTemplate = htmlTemplate.replace("${footer}", booking.getStatus().toUpperCase().equals(BookingStatus.APPROVED_BY_HOD.name()) ? "Please proceed any request" : "Please try it again");
                break;
            }
        }
        //static content
        htmlTemplate = htmlTemplate.replace("${bookingID}", booking.getBookingID());
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
