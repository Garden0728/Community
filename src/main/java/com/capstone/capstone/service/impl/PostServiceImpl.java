package com.capstone.capstone.service.impl;

import com.capstone.capstone.dto.request.Post.PostCreateRequest;
import com.capstone.capstone.dto.request.Post.PostUpdateRequest;
import com.capstone.capstone.dto.response.Post.PostResponse;
import com.capstone.capstone.entity.Post;
import com.capstone.capstone.entity.User;
import com.capstone.capstone.exception.ErrorCode;
import com.capstone.capstone.exception.Exception;
import com.capstone.capstone.repository.PostRepository;
import com.capstone.capstone.repository.UserRepository;
import com.capstone.capstone.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PostResponse save(PostCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new Exception(ErrorCode.USER_NOT_FOUND));
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .user(user)
                .build();
        return PostResponse.from(postRepository.save(post));
    }

    @Override
    public List<PostResponse> findAll() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    @Override
    public List<PostResponse> findByUserId(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    @Override
    public PostResponse findById(Long id) {
        return PostResponse.from(postRepository.findById(id)
                .orElseThrow(() -> new Exception(ErrorCode.POST_NOT_FOUND)));
    }

    @Override
    public PostResponse update(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new Exception(ErrorCode.POST_NOT_FOUND));
        User loginUser = userRepository.findById(request.userId())
                .orElseThrow(() -> new Exception(ErrorCode.USER_NOT_FOUND));
        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new Exception(ErrorCode.UPDATE_FORBIDDEN);
        }
        post.update(request.title(), request.content());
        return PostResponse.from(postRepository.save(post));
    }

    @Override
    public void delete(Long id, Long loginUserId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new Exception(ErrorCode.POST_NOT_FOUND));
        User loginUser = userRepository.findById(loginUserId)
                .orElseThrow(() -> new Exception(ErrorCode.USER_NOT_FOUND));
        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new Exception(ErrorCode.DELETE_FORBIDDEN);
        }
        postRepository.delete(post);
    }
}
