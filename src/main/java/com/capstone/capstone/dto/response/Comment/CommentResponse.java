package com.capstone.capstone.dto.response.Comment;

import com.capstone.capstone.entity.Comment;

import java.time.format.DateTimeFormatter;

public record CommentResponse(
        Long id,
        String content,
        Long userId,
        String loginId,
        String nickname,
        Long postId,
        String createdAt,
        String updatedAt
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getLoginId(),
                comment.getUser().getNickname(),
                comment.getPost().getId(),
                comment.getCreatedAt() != null ? comment.getCreatedAt().format(FORMATTER) : null,
                comment.getUpdatedAt() != null ? comment.getUpdatedAt().format(FORMATTER) : null
        );
    }
}
