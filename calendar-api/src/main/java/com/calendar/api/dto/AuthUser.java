package com.calendar.api.dto;

import lombok.Data;

@Data
public class AuthUser {
    private final Long id;

    public static AuthUser of(Long id) {
        return new AuthUser(id);
    }
}
