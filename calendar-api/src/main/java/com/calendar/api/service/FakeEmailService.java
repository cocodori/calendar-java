package com.calendar.api.service;

import com.calendar.core.domain.entity.Engagement;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class FakeEmailService implements EmailService{

    @Override
    public void sendEngagement(Engagement engagement) {
        System.out.println("send email.\nemail:"
                +engagement.getAttendee().getEmail()+
                "\nscheduleId:"+engagement.getSchedule().getId());
    }
}
