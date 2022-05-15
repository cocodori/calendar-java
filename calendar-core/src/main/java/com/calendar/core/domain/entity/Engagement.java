package com.calendar.core.domain.entity;

import com.calendar.core.domain.Event;
import com.calendar.core.domain.RequestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@Getter
@Table(name = "engagement")
@Entity
public class Engagement extends BaseEntity {
    @JoinColumn(name = "schedule_id")
    @ManyToOne
    private Schedule schedule;

    @JoinColumn(name = "attendee_id")
    @ManyToOne
    private User attendee;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;

    public Engagement(Schedule schedule, User attendee, RequestStatus requestStatus) {
        this.schedule = schedule;
        this.attendee = attendee;
        this.requestStatus = requestStatus;
    }

    public Event getEvent() {
        return schedule.toEvent();
    }
}
