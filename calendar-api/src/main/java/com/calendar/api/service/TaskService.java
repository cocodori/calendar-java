package com.calendar.api.service;

import com.calendar.api.dto.AuthUser;
import com.calendar.api.dto.TaskCreateReq;
import com.calendar.core.domain.entity.Schedule;
import com.calendar.core.domain.entity.repository.ScheduleRepository;
import com.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    public void create(TaskCreateReq taskCreateReq, AuthUser authUser) {
        final Schedule taskSchedule =
                Schedule.task(taskCreateReq.getTitle(),
                        taskCreateReq.getDescription(),
                        taskCreateReq.getTaskAt(),
                        userService.findByUserId(authUser.getId())
                        );

        scheduleRepository.save(taskSchedule);
    }
}
