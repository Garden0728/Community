package com.capstone.capstone.repository;

import com.capstone.capstone.entity.post.Category;
import com.capstone.capstone.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Post> findByCategoryOrderByCreatedAtDesc(Category category);
}
