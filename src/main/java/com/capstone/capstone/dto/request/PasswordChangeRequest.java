package com.capstone.capstone.dto.request;

public record PasswordChangeRequest(
        String currentPassword, String newPassword, String newPasswordConfirm
) {}
