package com.community.community.service;

import com.community.community.dto.request.Post.PostCreateRequest;
import com.community.community.dto.request.Post.PostUpdateRequest;
import com.community.community.dto.response.Post.PostResponse;
import com.community.community.entity.post.Category;

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
