package com.calendar.api.util;

import com.calendar.api.dto.EventDto;
import com.calendar.api.dto.NotificationDto;
import com.calendar.api.dto.ScheduleDto;
import com.calendar.api.dto.TaskDto;
import com.calendar.core.domain.entity.Schedule;
import com.calendar.core.exception.CalendarException;
import com.calendar.core.exception.ErrorCode;

public abstract class DtoConverter {
    public static ScheduleDto fromSchedule(Schedule schedule) {
        switch (schedule.getScheduleType()) {
            case EVENT:
                return EventDto.builder()
                        .scheduleId(schedule.getId())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .startAt(schedule.getStartAt())
                        .endAt(schedule.getEndAt())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case TASK:
                return TaskDto.builder()
                        .scheduleId(schedule.getId())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .taskAt(schedule.getStartAt())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case NOTIFICATION:
                return NotificationDto.builder()
                        .scheduleId(schedule.getId())
                        .title(schedule.getTitle())
                        .notifyAt(schedule.getStartAt())
                        .writerId(schedule.getWriter().getId())
                        .build();
            default:
                throw new CalendarException(ErrorCode.BAD_REQUEST);
        }
    }
}
