package com.community.community.service;

import com.community.community.dto.request.Comment.CommentCreateRequest;
import com.community.community.dto.request.Comment.CommentUpdateRequest;
import com.community.community.dto.response.Comment.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse save(CommentCreateRequest request);
    List<CommentResponse> findByPostId(Long postId);
    CommentResponse update(Long id, CommentUpdateRequest request);
    void delete(Long id, Long loginUserId);
}
