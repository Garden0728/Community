package com.capstone.capstone.comment;

import com.capstone.capstone.comment.dto.CommentCreateRequest;
import com.capstone.capstone.comment.dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/create")
    public CommentResponse createComment(@RequestBody CommentCreateRequest request) {
        return commentService.save(request);
    }

    // 특정 게시글 댓글 조회
    @GetMapping("/post/{postId}")
    public List<CommentResponse> getCommentsByPost(@PathVariable Long postId) {
        return commentService.findByPostId(postId);
    }
}