package com.calendar.api.dto;

public class AuthUser {
    private final Long id;

    private AuthUser(Long id) {
        this.id = id;
    }

    public static AuthUser of(Long id) {
        return new AuthUser(id);
    }

    public Long getId() {
        return id;
    }
}
