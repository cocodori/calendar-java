package com.calendar.core.domain;

import com.calendar.core.domain.entity.Schedule;
import com.calendar.core.domain.entity.ScheduleType;
import com.calendar.core.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DomainCreateTest {
    @Test
    void eventCreate() {
        User user = new User("eme", "email", "pw", LocalDate.now());
        final Schedule taskSchedule = Schedule.task("할 일", "청소", LocalDateTime.now(), user);

        assertEquals(taskSchedule.getScheduleType(), ScheduleType.TASK);
        assertEquals(taskSchedule.toTask().getTitle(), "할 일");
    }
}