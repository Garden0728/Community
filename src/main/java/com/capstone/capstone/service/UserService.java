package com.capstone.capstone.service;

import com.capstone.capstone.dto.request.User.UserSignupRequest;
import com.capstone.capstone.dto.request.User.UserUpdateRequest;
import com.capstone.capstone.dto.response.User.UserResponse;
import com.capstone.capstone.entity.user.User;

import java.util.List;

public interface UserService {
    UserResponse save(UserSignupRequest request);
    List<User> findAll();
    boolean isLoginIdDuplicate(String loginId);
    boolean isNicknameDuplicate(String nickname);
    boolean isPhoneDuplicate(String phone);
    User login(String loginId, String password);
    User findById(Long id);
    User update(Long id, UserUpdateRequest request);
    void delete(Long id);
    String findLoginId(String name, String phone);
    Long verifyByLoginIdAndPhone(String loginId, String phone);
    void changePassword(Long id, String currentPassword, String newPassword, String newPasswordConfirm);
    void resetPassword(Long id, String newPassword, String newPasswordConfirm);
}
