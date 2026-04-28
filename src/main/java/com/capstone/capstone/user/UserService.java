package com.capstone.capstone.user;

import com.capstone.capstone.mail.MailService;
import com.capstone.capstone.user.dto.UserSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MailService mailService;

    private final UserRepository userRepository;


    public User save(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("필수 값이 누락되었습니다.");
        }

        // 아이디 중복 체크
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 닉네임 중복 체크
        if (userRepository.existsByNickname(user.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        // 이메일 중복 체크
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (user.getGender() != 1 && user.getGender() != 2) {
            throw new IllegalArgumentException("성별은 1(남자) 또는 2(여자)만 가능합니다.");
        }


        // 등급 강제 설정
        user.setGrade(0);

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

    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
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

        // 닉네임 수정 (값 있을 때만)
        if (request.getNickname() != null) {
            if (!user.getNickname().equals(request.getNickname())
                    && userRepository.existsByNickname(request.getNickname())) {
                throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
            }
            user.setNickname(request.getNickname());
        }

        // 이메일 수정 (값 있을 때만)
        if (request.getEmail() != null) {
            if (!user.getEmail().equals(request.getEmail())
                    && userRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            }
            user.setEmail(request.getEmail());
        }

        // 이름 수정 (값 있을 때만)
        if (request.getName() != null) {
            user.setName(request.getName());
        }

        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));

        userRepository.delete(user);
    }

    public String findUsername(String name, String email) {
        User user = userRepository.findByNameAndEmail(name, email);

        if (user == null) {
            throw new IllegalArgumentException("일치하는 회원이 없습니다.");
        }

        return user.getUsername();
    }

    public String resetPassword(String username, String email) {

        System.out.println("request email = " + email); // 여기 1번

        User user = userRepository.findByUsernameAndEmail(username, email);

        if (user == null) {
            throw new IllegalArgumentException("일치하는 회원이 없습니다.");
        }

        System.out.println("db email = " + user.getEmail()); // 여기 2번

        String tempPassword = "temp" + (int)(Math.random() * 1000000);

        user.setPassword(tempPassword);
        userRepository.save(user);

        System.out.println("send to = " + user.getEmail()); // 여기 3번

        mailService.sendTempPassword(user.getEmail(), tempPassword);

        return tempPassword;
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

        user.setPassword(newPassword);
        userRepository.save(user);
    }
}