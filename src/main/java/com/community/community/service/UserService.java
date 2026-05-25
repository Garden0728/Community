package com.community.community.service;

import com.community.community.dto.request.User.UserSignupRequest;
import com.community.community.dto.request.User.UserUpdateRequest;
import com.community.community.dto.response.User.UserResponse;
import com.community.community.entity.user.User;

public interface UserService {
    UserResponse save(UserSignupRequest request);
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
