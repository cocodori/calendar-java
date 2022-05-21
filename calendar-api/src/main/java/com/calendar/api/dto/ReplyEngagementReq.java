package com.calendar.api.dto;

import com.calendar.core.domain.RequestReplyType;
import lombok.*;

@Data
public class ReplyEngagementReq {
    private RequestReplyType type;
}
