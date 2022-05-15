package com.calendar.api.controller;

import com.calendar.api.dto.AuthUser;
import com.calendar.api.dto.EventCreateReq;
import com.calendar.api.dto.TaskCreateReq;
import com.calendar.api.service.EventService;
import com.calendar.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final TaskService taskService;
    private final EventService eventService;

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(
            @RequestBody TaskCreateReq taskCreateReq,
            AuthUser authUser
    ) {
        taskService.create(taskCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/events")
    public ResponseEntity<Void> createEvent(
            @RequestBody EventCreateReq eventCreateReq,
            AuthUser authUser
    ) {
        eventService.create(eventCreateReq, authUser);
        return ResponseEntity.ok().build();
    }
}
