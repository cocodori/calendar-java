package com.calendar.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginReq {
    private final String email;
    private final String password;
}
