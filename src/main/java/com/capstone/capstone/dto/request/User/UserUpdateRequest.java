package com.capstone.capstone.dto.request.User;

import jakarta.validation.constraints.NotBlank;

//3개만 수정 할 수 있게 따로 dto 생성
public record UserUpdateRequest(@NotBlank(message = "이름은 필수입니다.") String name,
                                @NotBlank(message = "닉네임 필수입니다.")String nickname,
                                @NotBlank(message = "전화번호는 필수입니다.")String phone) {}
