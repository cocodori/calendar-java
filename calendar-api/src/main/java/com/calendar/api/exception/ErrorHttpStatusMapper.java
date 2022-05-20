package com.calendar.api.exception;

import com.calendar.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class ErrorHttpStatusMapper {
    public static HttpStatus mapToStatus(ErrorCode errorCode) {
        switch (errorCode) {
            case BAD_REQUEST:
            case VALIDATION_FAIL:
            case ALREADY_EXISTS_USER:
            case EVENT_CREATE_OVERLAPPED_PERIOD:
                return HttpStatus.BAD_REQUEST;
            case PASSWORD_NOT_MATCH:
            case USER_NOT_FOUND:
                return HttpStatus.UNAUTHORIZED;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
