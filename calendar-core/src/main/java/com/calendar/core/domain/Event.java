package com.calendar.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
public class Event {
    private Long id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String title;
    private String description;
    private User writer;
    private List<Engagement> engagements;
    private LocalDateTime createdAt;

    public Event(LocalDateTime startAt, LocalDateTime endAt, String title, String description, User writer, LocalDateTime createdAt) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.createdAt = createdAt;
    }

    public void addEngagement(Engagement engagement) {
        if (this.engagements == null)
            this.engagements = Arrays.asList(engagement);
        else
            this.engagements.add(engagement);

    }
}
