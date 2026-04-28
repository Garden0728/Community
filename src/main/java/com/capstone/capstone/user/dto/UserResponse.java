package com.capstone.capstone.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String username;
    private String name;
    private String nickname;
    private String email;
    private int grade;
    private LocalDateTime createdAt;
    private int gender;
}