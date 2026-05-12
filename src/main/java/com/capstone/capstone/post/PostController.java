package com.capstone.capstone.post;

import com.capstone.capstone.post.dto.PostCreateRequest;
import com.capstone.capstone.post.dto.PostResponse;
import com.capstone.capstone.post.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.capstone.capstone.config.JwtUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private final JwtUtil jwtUtil;

    // 게시글 생성
    @PostMapping("/create")
    public PostResponse createPost(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody PostCreateRequest request
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long userId = jwtUtil.getUserId(token);

        return postService.save(request, userId);
    }

    // 게시글 전체 조회
    @GetMapping("/list")
    public List<PostResponse> getPostList() {
        return postService.findAll();
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.findById(id);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public PostResponse updatePost(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody PostUpdateRequest request
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long loginUserId = jwtUtil.getUserId(token);

        return postService.update(id, request, loginUserId);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public String deletePost(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long loginUserId = jwtUtil.getUserId(token);

        postService.delete(id, loginUserId);

        return "게시글 삭제 완료";
    }

    // 특정 유저 게시글 조회
    @GetMapping("/user/{userId}")
    public List<PostResponse> getPostsByUser(@PathVariable Long userId) {
        return postService.findByUserId(userId);
    }
}