package com.capstone.capstone.controller;

import com.capstone.capstone.dto.request.User.PasswordChangeRequest;
import com.capstone.capstone.dto.request.User.UserLoginRequest;
import com.capstone.capstone.dto.request.User.UserSignupRequest;
import com.capstone.capstone.dto.request.User.UserUpdateRequest;
import com.capstone.capstone.dto.response.User.UserResponse;
import com.capstone.capstone.entity.user.User;
import com.capstone.capstone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public UserResponse createUser(@RequestBody @Valid UserSignupRequest request) {
        return userService.save(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return UserResponse.from(userService.findById(id));
    }

    @GetMapping("/check/loginId")
    public boolean checkLoginId(@RequestParam String loginId) {
        return userService.isLoginIdDuplicate(loginId);
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
        User user = userService.login(request.loginId(), request.password());
        return Map.of("userId", user.getId(), "loginId", user.getLoginId(), "nickname", user.getNickname());
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest request) {
        return UserResponse.from(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "회원 삭제 완료";
    }

    @PostMapping("/find-loginId")
    public Map<String, Object> findLoginId(@RequestBody Map<String, String> request) {
        return Map.of("loginId", userService.findLoginId(request.get("name"), request.get("phone")));
    }

    @PostMapping("/verify-for-password")
    public Map<String, Object> verifyForPassword(@RequestBody Map<String, String> request) {
        return Map.of("userId", userService.verifyByLoginIdAndPhone(request.get("loginId"), request.get("phone")));
    }

    @PutMapping("/{id}/password")
    public void changePassword(@PathVariable Long id, @RequestBody PasswordChangeRequest request) {
        userService.changePassword(id, request.currentPassword(), request.newPassword(), request.newPasswordConfirm());
    }

    @PutMapping("/{id}/reset-password")
    public void resetPassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        userService.resetPassword(id, request.get("newPassword"), request.get("newPasswordConfirm"));
    }
}
