package com.capstone.capstone.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {

    private String currentPassword;      // 현재 비밀번호
    private String newPassword;          // 새 비밀번호
    private String newPasswordConfirm;   // 새 비밀번호 확인
}