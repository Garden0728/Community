package com.capstone.capstone.dto.response.User;

import com.capstone.capstone.entity.User;

import java.time.format.DateTimeFormatter;

public record UserResponse(
        Long id,
        String loginId,
        String name,
        String nickname,
        String phone,
        int gender,
        String createdAt
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getLoginId(),
                user.getName(),
                user.getNickname(),
                user.getPhone(),
                user.getGender(),
                user.getCreatedAt() != null ? user.getCreatedAt().format(FORMATTER) : null
        );
    }
}
