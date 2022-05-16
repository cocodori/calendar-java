package com.calendar.api.dto;

import com.calendar.core.domain.entity.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class TaskDto implements ScheduleDto {

    private final Long scheduleId;
    private final LocalDateTime taskAt;
    private final String title;
    private final String description;
    private final Long writerId;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.TASK;
    }
}
