package com.capstone.capstone.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자동 증가 PK

    @Column(nullable = false, unique = true, length = 50)
    private String username; // 로그인 아이디 (중복 불가)

    @Column(nullable = false, length = 50)
    private String name; // 이름

    @Column(nullable = false, unique = true, length = 50)
    private String nickname; // 닉네임 (중복 불가)

    @Column(nullable = false, length = 255)
    private String password; // 비밀번호

    @Column(nullable = false, unique = true, length = 100)
    private String email; // 이메일 (중복 불가)

    @Column(nullable = false)
    private int grade = 0; // 0: 일반회원, 1: 관리자

    @Column(nullable = false)
    private int gender; // 1: 남자, 2: 여자

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}