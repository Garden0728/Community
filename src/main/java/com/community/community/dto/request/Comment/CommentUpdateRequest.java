package com.community.community.dto.request.Comment;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest(Long userId,
                                   @NotBlank(message = "내용은 필수입니다.") String content)
{}
