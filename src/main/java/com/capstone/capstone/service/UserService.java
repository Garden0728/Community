package com.capstone.capstone.service;

import com.capstone.capstone.dto.request.UserSignupRequest;
import com.capstone.capstone.entity.User;
import com.capstone.capstone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("필수 값이 누락되었습니다.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByNickname(user.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        if (!user.getPhone().matches("^01[016789]-?\\d{3,4}-?\\d{4}$")) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)");
        }
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
        }
        if (user.getGender() != 1 && user.getGender() != 2) {
            throw new IllegalArgumentException("성별은 1(남자) 또는 2(여자)만 가능합니다.");
        }
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean isUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean isPhoneDuplicate(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        }
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        return user;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
    }

    public User update(Long id, UserSignupRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        if (request.nickname() != null
                && !user.getNickname().equals(request.nickname())
                && userRepository.existsByNickname(request.nickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        if (request.phone() != null && !user.getPhone().equals(request.phone())) {
            if (!request.phone().matches("^01[016789]-?\\d{3,4}-?\\d{4}$")) {
                throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다.");
            }
            if (userRepository.existsByPhone(request.phone())) {
                throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
            }
        }
        user.updateProfile(request.name(), request.nickname(), request.phone());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        userRepository.delete(user);
    }

    public String findUsername(String name, String phone) {
        User user = userRepository.findByNameAndPhone(name, phone);
        if (user == null) {
            throw new IllegalArgumentException("일치하는 회원이 없습니다.");
        }
        return user.getUsername();
    }

    public Long verifyByUsernameAndPhone(String username, String phone) {
        User user = userRepository.findByUsernameAndPhone(username, phone);
        if (user == null) {
            throw new IllegalArgumentException("아이디 또는 전화번호가 일치하지 않습니다.");
        }
        return user.getId();
    }

    public void changePassword(Long id, String currentPassword, String newPassword, String newPasswordConfirm) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        if (!user.getPassword().equals(currentPassword)) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }
        user.changePassword(newPassword);
        userRepository.save(user);
    }

    public void resetPassword(Long id, String newPassword, String newPasswordConfirm) {
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        user.changePassword(newPassword);
        userRepository.save(user);
    }
}
