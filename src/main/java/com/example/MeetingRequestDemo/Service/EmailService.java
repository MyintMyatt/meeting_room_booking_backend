package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.Enum.UserRoles;
import com.example.MeetingRequestDemo.Model.Booking;
import com.example.MeetingRequestDemo.Model.Users;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Value("${spring.mail.username}")
    private String fromUser;


//    private String[] sendTo = new String[]{"flowtest@cp.com.mm", "ict-appimplement10@cp.com.mm"};

    public void sendMail(Booking booking, UserRoles sendToApproval) {
        try {
            String sendTo;
            String htmlTemplate;
            MimeMessage message = message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromUser);
            helper.setSubject("Meeting Room Request");
            if (sendToApproval != UserRoles.USER) {
                String department = sendToApproval == UserRoles.ADMIN ? "Admin" : booking.getDepartment();
                Users approver = userService.getApprover(department, sendToApproval.getRoleName());
                sendTo = approver.getUserEmail();
                htmlTemplate = fileService.readFileContentFromSources(booking, approver.getUserName(), sendToApproval);
                helper.setTo(sendTo);
                helper.setText(htmlTemplate, true);
                System.err.println(LocalDateTime.now() + ": " + booking.getBookingID() + " => Successfully sent mail to approver " + approver.getUserName() + "(" + approver.getUserEmail() + ")" );
            } else {
                htmlTemplate = fileService.readFileContentFromSources(booking, booking.getRequesterName(), sendToApproval);
                helper.setTo("ict-appimplement10@cp.com.mm");
                helper.setText(htmlTemplate, true);
                System.err.println(LocalDateTime.now() + ": " + booking.getBookingID() + " => Successfully sent mail to " + booking.getRequesterName() + "(" + ")");
            }
            //helper.addAttachment("logo.png", new File(getClass().getResource("/templates/logo.png").toURI()));
            helper.addInline("logo.png", new File(getClass().getResource("/templates/logo.png").toURI()));
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
