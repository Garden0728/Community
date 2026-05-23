package com.capstone.capstone.controller;

import com.capstone.capstone.dto.request.CommentCreateRequest;
import com.capstone.capstone.dto.request.CommentUpdateRequest;
import com.capstone.capstone.dto.response.CommentResponse;
import com.capstone.capstone.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public CommentResponse createComment(@RequestBody CommentCreateRequest request) {
        return commentService.save(request);
    }

    @GetMapping("/post/{postId}")
    public List<CommentResponse> getCommentsByPost(@PathVariable Long postId) {
        return commentService.findByPostId(postId);
    }

    @PatchMapping("/{id}")
    public CommentResponse updateComment(@PathVariable Long id, @RequestBody CommentUpdateRequest request) {
        return commentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id, @RequestParam Long userId) {
        commentService.delete(id, userId);
        return "댓글 삭제 완료";
    }
}
