package com.capstone.capstone.dto.request;

public record CommentCreateRequest(
        String content, Long userId, Long postId
) {}
