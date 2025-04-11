package com.example.MeetingRequestDemo.Service;

import com.example.MeetingRequestDemo.DTOs.BookingDTO;
import com.example.MeetingRequestDemo.Enum.UserRoles;
import com.example.MeetingRequestDemo.Model.Booking;
import com.example.MeetingRequestDemo.Model.Users;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.File;


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

    public void sendMail(Booking booking) {
        try {
            Users approver = userService.getApprover(booking.getDepartment(), UserRoles.HOD.getRoleName());
            String sendTo = approver.getUserEmail();
            String htmlTemplate = fileService.readFileContentFromSources(booking, approver.getUserName());
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromUser);
            helper.setTo(sendTo);

            helper.setSubject("Hello from Spring Boot");
            helper.setText(htmlTemplate, true);

            helper.addInline("logo.png", new File(getClass().getResource("/templates/logo.png").toURI()));
            //helper.addAttachment("logo.png", new File(getClass().getResource("/templates/logo.png").toURI()));
            mailSender.send(message);
            System.err.println("Successfully sent mail to " + approver.getUserName() + "(" + approver.getUserEmail() + ")");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
