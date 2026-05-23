package com.capstone.capstone.dto.request.Comment;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest(@NotBlank(message = "내용은 필수입니다.") String content, Long userId, Long postId) {}
