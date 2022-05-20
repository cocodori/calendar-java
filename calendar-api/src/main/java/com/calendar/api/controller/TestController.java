package com.calendar.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
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

    @GetMapping("/test/template")
    public String testTemplate(Model model) {
        Map<String, Object> props = new HashMap<>();
        props.put("title", "타이틀");
        props.put("calendar", "sample@gmail.com");
        props.put("period", "언제부터언제까지");
        props.put("attendees", List.of("t1, t2, t3"));
        props.put("acceptUrl", "http://localhost:8080");
        props.put("rejectUrl", "http://localhost:8080");

        model.addAllAttributes(props);

        return "engagement-email";

    }
}
