package com.capstone.capstone.dto.request.Post;

import jakarta.validation.constraints.NotBlank;

public record PostCreateRequest(Long userId, @NotBlank(message = "제목은 필수입니다.")String title, @NotBlank(message = "내용은 필수입니다.")String content) {}
