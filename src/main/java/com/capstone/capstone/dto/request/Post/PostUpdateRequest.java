package com.capstone.capstone.dto.request.Post;

import com.capstone.capstone.entity.post.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostUpdateRequest(
        Long userId,
        @NotNull(message = "카테고리를 선택해 주세요.") Category category,
        @NotBlank(message = "제목은 필수입니다.") String title,
        @NotBlank(message = "내용은 필수입니다.") String content
) {}
