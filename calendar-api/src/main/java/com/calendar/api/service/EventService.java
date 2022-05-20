package com.calendar.api.service;

import com.calendar.api.dto.AuthUser;
import com.calendar.api.dto.EventCreateReq;
import com.calendar.core.domain.RequestStatus;
import com.calendar.core.domain.entity.Engagement;
import com.calendar.core.domain.entity.Schedule;
import com.calendar.core.domain.entity.User;
import com.calendar.core.domain.entity.repository.EngagementRepository;
import com.calendar.core.domain.entity.repository.ScheduleRepository;
import com.calendar.core.exception.CalendarException;
import com.calendar.core.exception.ErrorCode;
import com.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EngagementRepository engagementRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;
    private final EmailService emailService;

    @Transactional
    public void create(
            EventCreateReq eventCreateReq,
            AuthUser authUser
    ) {
        //이벤트 참여자의 다른 이벤트와 중복되면 안 됨.
        //1~2시 회의가 있는데, 추가로 다른 회의를 등록할 수 없다. -> 미팅을 두 개 잡을 수 없다.
        //+이메일 발송
        final List<Engagement> engagements = engagementRepository.findAll(); //TODO findAll 개선
        if (engagements.stream()
                .anyMatch(e ->
                        e.getRequestStatus() == RequestStatus.ACCEPTED &&
                        e.getEvent().isOverlapped(
                                eventCreateReq.getStartAt(),
                                eventCreateReq.getEndAt())
                )) {
            throw new CalendarException(ErrorCode.EVENT_CREATE_OVERLAPPED_PERIOD);
        }
        final Schedule eventSchedule = Schedule.event(
                eventCreateReq.getTitle(),
                eventCreateReq.getDescription(),
                eventCreateReq.getStartAt(),
                eventCreateReq.getEndAt(),
                userService.findByUserId(authUser.getId())
        );

        scheduleRepository.save(eventSchedule);
        eventCreateReq.getAttendeeIds()
                .forEach(attendeeId -> {
                    final User attendee = userService.findByUserId(attendeeId);
                    final Engagement engagement = Engagement.builder()
                            .schedule(eventSchedule)
                            .requestStatus(RequestStatus.REQUESTED)
                            .attendee(attendee)
                            .build();

                    engagementRepository.save(engagement);
                    emailService.sendEngagement(engagement);
                });
    }
}
