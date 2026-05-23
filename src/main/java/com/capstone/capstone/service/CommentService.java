package com.capstone.capstone.service;

import com.capstone.capstone.dto.request.CommentCreateRequest;
import com.capstone.capstone.dto.request.CommentUpdateRequest;
import com.capstone.capstone.dto.response.CommentResponse;
import com.capstone.capstone.entity.Comment;
import com.capstone.capstone.entity.Post;
import com.capstone.capstone.entity.User;
import com.capstone.capstone.repository.CommentRepository;
import com.capstone.capstone.repository.PostRepository;
import com.capstone.capstone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponse save(CommentCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
        Comment comment = Comment.builder()
                .content(request.content())
                .user(user)
                .post(post)
                .build();
        return CommentResponse.from(commentRepository.save(comment));
    }

    public List<CommentResponse> findByPostId(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    public CommentResponse update(Long id, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 없습니다."));
        User loginUser = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        if (!comment.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        comment.update(request.content());
        return CommentResponse.from(commentRepository.save(comment));
    }

    public void delete(Long id, Long loginUserId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 없습니다."));
        User loginUser = userRepository.findById(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        if (!comment.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }
}
