package com.capstone.capstone.dto.response;

import com.capstone.capstone.entity.Post;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        Long userId,
        String username,
        String nickname,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser() != null ? post.getUser().getId() : null,
                post.getUser() != null ? post.getUser().getUsername() : null,
                post.getUser() != null ? post.getUser().getNickname() : null,
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
