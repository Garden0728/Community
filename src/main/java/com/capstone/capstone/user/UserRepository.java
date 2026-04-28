package com.capstone.capstone.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    User findByUsername(String username);

    User findByNameAndEmail(String name, String email);

    User findByUsernameAndEmail(String username, String email);

}