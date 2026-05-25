package com.capstone.capstone.service;

import com.capstone.capstone.dto.request.Post.PostCreateRequest;
import com.capstone.capstone.dto.request.Post.PostUpdateRequest;
import com.capstone.capstone.dto.response.Post.PostResponse;
import com.capstone.capstone.entity.post.Category;

import java.util.List;

public interface PostService {
    PostResponse save(PostCreateRequest request);
    List<PostResponse> findAll();
    List<PostResponse> findByCategory(Category category);
    PostResponse findById(Long id);
    List<PostResponse> findByUserId(Long userId);
    PostResponse update(Long id, PostUpdateRequest request);
    void delete(Long id, Long loginUserId);
}
