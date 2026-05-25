package com.community.community.controller;

import com.community.community.dto.request.Comment.CommentCreateRequest;
import com.community.community.dto.request.Comment.CommentUpdateRequest;
import com.community.community.dto.response.Comment.CommentResponse;
import com.community.community.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController { //댓글 컨트롤러

    private final CommentService commentService; //commentService DI

    @PostMapping("/create") //
    public CommentResponse createComment(@RequestBody @Valid CommentCreateRequest request) {
        return commentService.save(request);
    }

    @GetMapping("/post/{postId}")
    public List<CommentResponse> getCommentsByPost(@PathVariable Long postId) {
        return commentService.findByPostId(postId);
    }

    @PatchMapping("/{id}")
    public CommentResponse updateComment(@PathVariable Long id, @RequestBody @Valid CommentUpdateRequest request) {
        return commentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id, @RequestParam Long userId) {
        commentService.delete(id, userId);
        return "댓글 삭제 완료";
    }
}
