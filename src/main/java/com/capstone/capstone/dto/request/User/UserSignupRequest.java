package com.capstone.capstone.dto.request.User;

import jakarta.validation.constraints.NotBlank;

public record UserSignupRequest(
        @NotBlank(message = "아이디는 필수입니다.")String loginId,
        @NotBlank(message = "이름은 필수입니다.")String name,
        @NotBlank(message = "닉네임 필수입니다.")String nickname,
        @NotBlank(message = "전화번호는 필수입니다.")String phone,
        @NotBlank(message = "비밀번호는 필수입니다.")String password,
        @NotBlank(message = "비밀번호 확인은 필수입니다.")String passwordConfirm,
        int gender
) {}
