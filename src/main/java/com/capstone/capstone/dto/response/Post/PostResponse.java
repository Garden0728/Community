package com.capstone.capstone.dto.response.Post;

import com.capstone.capstone.entity.Post;

import java.time.format.DateTimeFormatter;

public record PostResponse(
        Long id,
        String title,
        String content,
        Long userId,
        String loginId,
        String nickname,
        String createdAt,
        String updatedAt
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser() != null ? post.getUser().getId() : null,
                post.getUser() != null ? post.getUser().getLoginId() : null,
                post.getUser() != null ? post.getUser().getNickname() : null,
                post.getCreatedAt() != null ? post.getCreatedAt().format(FORMATTER) : null,
                post.getUpdatedAt() != null ? post.getUpdatedAt().format(FORMATTER) : null
        );
    }
}
