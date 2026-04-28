package com.capstone.capstone.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {

    private String username;
    private String name;
    private String nickname;
    private String password;
    private String passwordConfirm;
    private String email;
    private int gender;
}