package com.be.restaurant.domain.service;

import jakarta.mail.MessagingException;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@EnableAutoConfiguration
public class EmailService {


    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public void sendEmail(String toEmail, byte[] pdfContent, String emailContent, String fileName) throws MessagingException {

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(toEmail);
            messageHelper.setSubject("THÔNG BÁO PHIẾU BẢO HÀNH");
            messageHelper.setText(emailContent, true); // true indicates HTML content
            if (pdfContent != null) {
                ByteArrayDataSource source =  new ByteArrayDataSource(pdfContent, "application/pdf");
                messageHelper.addAttachment(String.format("%s.pdf", fileName), source);
            }

        };

        try {
            javaMailSender.send(preparator);
            System.out.println("Email sent successfully with PDF attachment.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

