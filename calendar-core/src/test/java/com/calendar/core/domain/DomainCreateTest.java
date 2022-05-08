package com.calendar.core.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DomainCreateTest {
    @Test
    void eventCreate() {
        User writer = new User("writer", "writer@email.com", "pw", LocalDate.now(), LocalDateTime.now());
        User attendee = new User("attendee", "attendee@email.com", "pw", LocalDate.now(), LocalDateTime.now());
        Event event = new Event(
                LocalDateTime.now(),
                LocalDateTime.now(),
                "title",
                "description",
                writer,
                LocalDateTime.now()
        );

        event.addEngagement(new Engagement(
                event,
                attendee,
                LocalDateTime.now(),
                RequestStatus.REQUESTED
        ));

        assertEquals(event.getEngagements().get(0).getEvent().getWriter().getName(), "writer");

    }

}