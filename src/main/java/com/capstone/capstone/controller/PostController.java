package com.capstone.capstone.controller;

import com.capstone.capstone.dto.request.PostCreateRequest;
import com.capstone.capstone.dto.request.PostUpdateRequest;
import com.capstone.capstone.dto.response.PostResponse;
import com.capstone.capstone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public PostResponse createPost(@RequestBody PostCreateRequest request) {
        return postService.save(request);
    }

    @GetMapping("/list")
    public List<PostResponse> getPostList() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PutMapping("/{id}")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
        return postService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id, @RequestParam Long userId) {
        postService.delete(id, userId);
        return "게시글 삭제 완료";
    }

    @GetMapping("/user/{userId}")
    public List<PostResponse> getPostsByUser(@PathVariable Long userId) {
        return postService.findByUserId(userId);
    }
}
