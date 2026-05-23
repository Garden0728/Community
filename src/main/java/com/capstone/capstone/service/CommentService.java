package com.capstone.capstone.service;

import com.capstone.capstone.dto.request.Comment.CommentCreateRequest;
import com.capstone.capstone.dto.request.Comment.CommentUpdateRequest;
import com.capstone.capstone.dto.response.Comment.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse save(CommentCreateRequest request);
    List<CommentResponse> findByPostId(Long postId);
    CommentResponse update(Long id, CommentUpdateRequest request);
    void delete(Long id, Long loginUserId);
}
