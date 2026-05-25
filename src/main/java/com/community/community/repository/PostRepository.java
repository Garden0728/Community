package com.community.community.repository;

import com.community.community.entity.post.Category;
import com.community.community.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findByCategoryOrderByCreatedAtDesc(Category category);

    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
}
