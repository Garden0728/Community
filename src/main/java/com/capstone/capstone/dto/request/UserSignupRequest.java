package com.capstone.capstone.dto.request;

public record UserSignupRequest(
        String username,
        String name,
        String nickname,
        String password,
        String passwordConfirm,
        String phone,
        int gender
) {}
