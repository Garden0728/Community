package com.capstone.capstone.repository;

import com.capstone.capstone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    boolean existsByPhone(String phone);

    User findByUsername(String username);

    User findByNameAndPhone(String name, String phone);

    User findByUsernameAndPhone(String username, String phone);
}
