package com.community.community.dto.request.User;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequest(
        @NotBlank(message = "기존 비밀번호는 필수입니다.")String currentPassword,
        @NotBlank(message = "새로운 비밀번호는 필수입니다.")String newPassword,
        String newPasswordConfirm
) {}
