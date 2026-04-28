package com.capstone.capstone.user;

import com.capstone.capstone.mail.MailService;
import com.capstone.capstone.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final MailService mailService;

    private final UserService userService;

    // 회원 생성
    @PostMapping("/create")
    public UserResponse createUser(@RequestBody UserSignupRequest request) {

        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setNickname(request.getNickname());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());

        User savedUser = userService.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setName(savedUser.getName());
        response.setNickname(savedUser.getNickname());
        response.setEmail(savedUser.getEmail());
        response.setGrade(savedUser.getGrade());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setGender(savedUser.getGender());

        return response;
    }

    // 전체 조회
    @GetMapping("/list")
    public List<User> getUserList() {
        return userService.findAll();
    }

    // 아이디 중복 체크
    @GetMapping("/check/username")
    public boolean checkUsername(@RequestParam String username) {
        return userService.isUsernameDuplicate(username);
    }

    // 닉네임 중복 체크
    @GetMapping("/check/nickname")
    public boolean checkNickname(@RequestParam String nickname) {
        return userService.isNicknameDuplicate(nickname);
    }

    // 이메일 중복 체크
    @GetMapping("/check/email")
    public boolean checkEmail(@RequestParam String email) {
        return userService.isEmailDuplicate(email);
    }

    // 로그인
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserLoginRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            User user = userService.login(
                    request.getUsername(),
                    request.getPassword()
            );

            result.put("success", true);
            result.put("message", "로그인 성공");
            result.put("username", user.getUsername());
            result.put("nickname", user.getNickname());

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        User user = userService.findById(id);

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setName(user.getName());
        response.setNickname(user.getNickname());
        response.setEmail(user.getEmail());
        response.setGrade(user.getGrade());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id,
                                   @RequestBody UserSignupRequest request) {

        User updatedUser = userService.update(id, request);

        UserResponse response = new UserResponse();
        response.setId(updatedUser.getId());
        response.setUsername(updatedUser.getUsername());
        response.setName(updatedUser.getName());
        response.setNickname(updatedUser.getNickname());
        response.setEmail(updatedUser.getEmail());
        response.setGrade(updatedUser.getGrade());
        response.setCreatedAt(updatedUser.getCreatedAt());

        return response;
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
            String username = userService.findUsername(
                    request.get("name"),
                    request.get("email")
            );

            result.put("success", true);
            result.put("username", username);

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }
    @PostMapping("/reset-password")
    public Map<String, Object> resetPassword(@RequestBody PasswordResetRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            String tempPassword = userService.resetPassword(
                    request.getUsername(),
                    request.getEmail()
            );

            result.put("success", true);
            result.put("message", "임시 비밀번호가 발급되었습니다.");
            result.put("tempPassword", tempPassword);

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;

    }
    @PutMapping("/{id}/password")
    public Map<String, Object> changePassword(@PathVariable Long id,
                                              @RequestBody PasswordChangeRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            userService.changePassword(
                    id,
                    request.getCurrentPassword(),
                    request.getNewPassword(),
                    request.getNewPasswordConfirm()
            );

            result.put("success", true);
            result.put("message", "비밀번호가 변경되었습니다.");

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

}