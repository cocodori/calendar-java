package com.calendar.api.service;

import com.calendar.core.domain.entity.Engagement;

public interface EmailService {
    void sendEngagement(Engagement engagement);
}
