package com.capstone.capstone.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 전체 게시글 최신순 조회
    List<Post> findAllByOrderByCreatedAtDesc();

    // 특정 유저 게시글 최신순 조회
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
}