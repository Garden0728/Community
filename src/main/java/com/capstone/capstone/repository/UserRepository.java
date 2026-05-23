package com.capstone.capstone.repository;

import com.capstone.capstone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLoginId(String loginId);

    boolean existsByNickname(String nickname);

    boolean existsByPhone(String phone);

    User findByLoginId(String loginId);

    User findByNameAndPhone(String name, String phone);

    User findByLoginIdAndPhone(String loginId, String phone);
}
