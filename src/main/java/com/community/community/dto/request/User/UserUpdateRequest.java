package com.community.community.dto.request.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(
        @NotBlank(message = "이름은 필수입니다.") String name,
        @NotBlank(message = "닉네임은 필수입니다.") String nickname,
        @NotBlank(message = "전화번호는 필수입니다.")
        @Pattern(regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)") String phone
) {}
