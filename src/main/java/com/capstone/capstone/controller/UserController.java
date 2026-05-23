package com.capstone.capstone.controller;

import com.capstone.capstone.dto.request.PasswordChangeRequest;
import com.capstone.capstone.dto.request.UserLoginRequest;
import com.capstone.capstone.dto.request.UserSignupRequest;
import com.capstone.capstone.dto.response.UserResponse;
import com.capstone.capstone.entity.User;
import com.capstone.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public UserResponse createUser(@RequestBody UserSignupRequest request) {
        if (!request.password().equals(request.passwordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        User user = User.builder()
                .username(request.username())
                .name(request.name())
                .nickname(request.nickname())
                .password(request.password())
                .phone(request.phone())
                .gender(request.gender())
                .build();
        return UserResponse.from(userService.save(user));
    }

    @GetMapping("/list")
    public List<UserResponse> getUserList() {
        return userService.findAll().stream().map(UserResponse::from).toList();
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return UserResponse.from(userService.findById(id));
    }

    @GetMapping("/check/username")
    public boolean checkUsername(@RequestParam String username) {
        return userService.isUsernameDuplicate(username);
    }

    @GetMapping("/check/nickname")
    public boolean checkNickname(@RequestParam String nickname) {
        return userService.isNicknameDuplicate(nickname);
    }

    @GetMapping("/check/phone")
    public boolean checkPhone(@RequestParam String phone) {
        return userService.isPhoneDuplicate(phone);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserLoginRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.login(request.username(), request.password());
            result.put("success", true);
            result.put("userId", user.getId());
            result.put("username", user.getUsername());
            result.put("nickname", user.getNickname());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserSignupRequest request) {
        return UserResponse.from(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "회원 삭제 완료";
    }

    @PostMapping("/find-username")
    public Map<String, Object> findUsername(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("username", userService.findUsername(request.get("name"), request.get("phone")));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/verify-for-password")
    public Map<String, Object> verifyForPassword(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Long userId = userService.verifyByUsernameAndPhone(request.get("username"), request.get("phone"));
            result.put("success", true);
            result.put("userId", userId);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PutMapping("/{id}/password")
    public Map<String, Object> changePassword(@PathVariable Long id, @RequestBody PasswordChangeRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.changePassword(id, request.currentPassword(), request.newPassword(), request.newPasswordConfirm());
            result.put("success", true);
            result.put("message", "비밀번호가 변경되었습니다.");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PutMapping("/{id}/reset-password")
    public Map<String, Object> resetPassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.resetPassword(id, request.get("newPassword"), request.get("newPasswordConfirm"));
            result.put("success", true);
            result.put("message", "비밀번호가 변경되었습니다.");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
