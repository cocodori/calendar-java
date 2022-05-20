package com.calendar.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final JavaMailSender mailSender;

    @GetMapping("/api/mail")
    public void sendMailTest() {
        final MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo("leejihun1024@gmail.com");
            helper.setSubject("Test Mail!");
            helper.setText("Text....");
        };
        mailSender.send(preparator);
    }
}
