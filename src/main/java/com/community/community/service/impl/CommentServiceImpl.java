package com.community.community.service.impl;

import com.community.community.dto.request.Comment.CommentCreateRequest;
import com.community.community.dto.request.Comment.CommentUpdateRequest;
import com.community.community.dto.response.Comment.CommentResponse;
import com.community.community.entity.post.Comment;
import com.community.community.entity.post.Post;
import com.community.community.entity.user.User;
import com.community.community.exception.ErrorCode;
import com.community.community.exception.Exception;
import com.community.community.repository.CommentRepository;
import com.community.community.repository.PostRepository;
import com.community.community.repository.UserRepository;
import com.community.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public CommentResponse save(CommentCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new Exception(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new Exception(ErrorCode.POST_NOT_FOUND));
        Comment comment = Comment.builder()
                .content(request.content())
                .user(user)
                .post(post)
                .build();
        return CommentResponse.from(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponse> findByPostId(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
    @Transactional
    @Override
    public CommentResponse update(Long id, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new Exception(ErrorCode.COMMENT_NOT_FOUND));
        User loginUser = userRepository.findById(request.userId())
                .orElseThrow(() -> new Exception(ErrorCode.USER_NOT_FOUND));
        if (!comment.getUser().getId().equals(loginUser.getId())) {
            throw new Exception(ErrorCode.UPDATE_FORBIDDEN);
        }
        comment.update(request.content());
        return CommentResponse.from(comment);
    }
    @Transactional
    @Override
    public void delete(Long id, Long loginUserId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new Exception(ErrorCode.COMMENT_NOT_FOUND));
        User loginUser = userRepository.findById(loginUserId)
                .orElseThrow(() -> new Exception(ErrorCode.USER_NOT_FOUND));
        if (!comment.getUser().getId().equals(loginUser.getId())) {
            throw new Exception(ErrorCode.DELETE_FORBIDDEN);
        }
        commentRepository.delete(comment);
    }
}
