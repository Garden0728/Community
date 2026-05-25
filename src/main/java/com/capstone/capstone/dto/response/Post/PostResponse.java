package com.capstone.capstone.dto.response.Post;

import com.capstone.capstone.entity.post.Category;
import com.capstone.capstone.entity.post.Post;

import java.time.format.DateTimeFormatter;

public record PostResponse(
        Long id,
        Category category,
        String categoryName,
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
                post.getCategory(),
                post.getCategory() != null ? post.getCategory().getDisplayName() : null,
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
