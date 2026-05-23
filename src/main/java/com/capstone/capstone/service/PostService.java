package com.capstone.capstone.service;

import com.capstone.capstone.dto.request.PostCreateRequest;
import com.capstone.capstone.dto.request.PostUpdateRequest;
import com.capstone.capstone.dto.response.PostResponse;
import com.capstone.capstone.entity.Post;
import com.capstone.capstone.entity.User;
import com.capstone.capstone.repository.PostRepository;
import com.capstone.capstone.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse save(PostCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .user(user)
                .build();
        return PostResponse.from(postRepository.save(post));
    }

    public List<PostResponse> findAll() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    public List<PostResponse> findByUserId(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
        return PostResponse.from(post);
    }

    public PostResponse update(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
        User loginUser = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        post.update(request.title(), request.content());
        return PostResponse.from(postRepository.save(post));
    }

    public void delete(Long id, Long loginUserId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
        User loginUser = userRepository.findById(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        postRepository.delete(post);
    }
}
