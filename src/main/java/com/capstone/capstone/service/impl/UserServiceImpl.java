package com.capstone.capstone.service.impl;

import com.capstone.capstone.dto.request.User.UserSignupRequest;
import com.capstone.capstone.dto.request.User.UserUpdateRequest;
import com.capstone.capstone.dto.response.User.UserResponse;
import com.capstone.capstone.entity.user.User;
import com.capstone.capstone.exception.ErrorCode;
import com.capstone.capstone.exception.Exception;
import com.capstone.capstone.repository.UserRepository;
import com.capstone.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Transactional
    @Override
    public UserResponse save(UserSignupRequest request) {
        if (!request.password().equals(request.passwordConfirm())) {
            throw new Exception(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }
        if (userRepository.existsByLoginId(request.loginId())) {
            throw new Exception(ErrorCode.LOGIN_ID_DUPLICATE);
        }
        if (userRepository.existsByNickname(request.nickname())) {
            throw new Exception(ErrorCode.NICKNAME_DUPLICATE);
        }
        if (userRepository.existsByPhone(request.phone())) {
            throw new Exception(ErrorCode.PHONE_DUPLICATE);
        }
        User user = User.builder()
                .loginId(request.loginId())
                .name(request.name())
                .nickname(request.nickname())
                .password(request.password())
                .phone(request.phone())
                .gender(request.gender())
                .build();
        return UserResponse.from(userRepository.save(user));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean isLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    @Override
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public boolean isPhoneDuplicate(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public User login(String loginId, String password) {
        User user = userRepository.findByLoginId(loginId);
        if (user == null) {
            throw new Exception(ErrorCode.LOGIN_ID_NOT_FOUND);
        }
        if (!user.getPassword().equals(password)) {
            throw new Exception(ErrorCode.PASSWORD_MISMATCH);
        }
        return user;
    }

    @Override
    public User findById(Long id) {
        return findUser(id);
    }
    @Transactional
    @Override
    public User update(Long id, UserUpdateRequest request) {
        User user = findUser(id);
        if (request.nickname() != null
                && !user.getNickname().equals(request.nickname())
                && userRepository.existsByNickname(request.nickname())) {
            throw new Exception(ErrorCode.NICKNAME_DUPLICATE);
        }
        if (!user.getPhone().equals(request.phone()) && userRepository.existsByPhone(request.phone())) {
            throw new Exception(ErrorCode.PHONE_DUPLICATE);
        }
        user.updateProfile(request.name(), request.nickname(), request.phone());
        return userRepository.save(user);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.delete(findUser(id));
    }

    @Override
    public String findLoginId(String name, String phone) {
        User user = userRepository.findByNameAndPhone(name, phone);
        if (user == null) {
            throw new Exception(ErrorCode.USER_MATCH_NOT_FOUND);
        }
        return user.getLoginId();
    }

    @Override
    public Long verifyByLoginIdAndPhone(String loginId, String phone) {
        User user = userRepository.findByLoginIdAndPhone(loginId, phone);
        if (user == null) {
            throw new Exception(ErrorCode.USER_NOT_VERIFIED);
        }
        return user.getId();
    }

    @Override
    public void changePassword(Long id, String currentPassword, String newPassword, String newPasswordConfirm) {
        User user = findUser(id);
        if (!user.getPassword().equals(currentPassword)) {
            throw new Exception(ErrorCode.CURRENT_PASSWORD_MISMATCH);
        }
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new Exception(ErrorCode.NEW_PASSWORD_MISMATCH);
        }
        user.changePassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(Long id, String newPassword, String newPasswordConfirm) {
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new Exception(ErrorCode.NEW_PASSWORD_MISMATCH);
        }
        User user = findUser(id);
        user.changePassword(newPassword);
        userRepository.save(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception(ErrorCode.USER_NOT_FOUND));
    }
}
