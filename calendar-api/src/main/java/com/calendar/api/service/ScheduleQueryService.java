package com.calendar.api.service;

import com.calendar.api.dto.AuthUser;
import com.calendar.api.dto.ScheduleDto;
import com.calendar.api.util.DtoConverter;
import com.calendar.core.domain.entity.Engagement;
import com.calendar.core.domain.entity.Schedule;
import com.calendar.core.domain.entity.repository.EngagementRepository;
import com.calendar.core.domain.entity.repository.ScheduleRepository;
import com.calendar.core.util.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleQueryService {
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    public List<ScheduleDto> getScheduleByDay(
            AuthUser authUser,
            LocalDate date
    ) {
        final Period period = Period.of(date, date);
        return getScheduleByPeriod(authUser, period);
    }


    public List<ScheduleDto> getScheduleByWeek(AuthUser authUser, LocalDate startOfWeek) {
        final Period period = Period.of(startOfWeek, startOfWeek.plusDays(6));
        return getScheduleByPeriod(authUser, period);
    }

    public List<ScheduleDto> getScheduleByMonth(AuthUser authUser, YearMonth yearMonth) {
        final Period period = Period.of(yearMonth.atDay(1), yearMonth.atEndOfMonth());
        return getScheduleByPeriod(authUser, period);
    }

    private List<ScheduleDto> getScheduleByPeriod(AuthUser authUser, Period period) {
        return Stream.concat(
                scheduleRepository.findAllByWriter_Id(authUser.getId())
                        .stream()
                        .filter(schedule -> schedule.isOverlapped(period))
                        .map(DtoConverter::fromSchedule),
                engagementRepository.findAllByAttendee_Id(authUser.getId())
                        .stream()
                        .filter(engagement -> engagement.isOverlapped(period))
                        .map(engagement -> DtoConverter.fromSchedule(engagement.getSchedule())))
                .collect(Collectors.toList());
    }
}
