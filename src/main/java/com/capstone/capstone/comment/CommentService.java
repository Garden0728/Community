package com.capstone.capstone.comment;

import com.capstone.capstone.comment.dto.CommentCreateRequest;
import com.capstone.capstone.comment.dto.CommentResponse;
import com.capstone.capstone.post.Post;
import com.capstone.capstone.post.PostRepository;
import com.capstone.capstone.user.User;
import com.capstone.capstone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    // 댓글 생성
    public CommentResponse save(CommentCreateRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        Comment comment = new Comment();

        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    // 특정 게시글 댓글 조회
    public List<CommentResponse> findByPostId(Long postId) {

        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
}