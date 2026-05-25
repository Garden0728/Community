package com.community.community.dto.request.User;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(@NotBlank(message = "아이디는 필수입니다.")String loginId,
                               @NotBlank(message = "패스워드는 필수입니다.")String password) {}
