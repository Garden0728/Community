package com.capstone.capstone.dto.response;

import com.capstone.capstone.entity.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String username,
        String name,
        String nickname,
        String phone,
        int gender,
        LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getNickname(),
                user.getPhone(),
                user.getGender(),
                user.getCreatedAt()
        );
    }
}
