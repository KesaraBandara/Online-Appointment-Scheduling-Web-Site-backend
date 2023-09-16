package com.thejobs.onlineappointmentschedulingwebsite.service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendConfirmationEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Appointment Confirmation");
        message.setText("Your appointment has been confirmed.");

        javaMailSender.send(message);
    }

    public void sendCancellationEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Appointment Cancellation");
        message.setText("Your appointment has been canceled. try another day");

        javaMailSender.send(message);
    }
}
