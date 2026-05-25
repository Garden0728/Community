package com.community.community.service.impl;

import com.community.community.dto.request.Post.PostCreateRequest;
import com.community.community.dto.request.Post.PostUpdateRequest;
import com.community.community.dto.response.Post.PostResponse;
import com.community.community.entity.post.Category;
import com.community.community.entity.post.Post;
import com.community.community.entity.user.User;
import com.community.community.exception.ErrorCode;
import com.community.community.exception.Exception;
import com.community.community.repository.PostRepository;
import com.community.community.repository.UserRepository;
import com.community.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
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
                .category(request.category())
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
    public List<PostResponse> findByCategory(Category category) {
        return postRepository.findByCategoryOrderByCreatedAtDesc(category)
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
    public List<PostResponse> findByUserId(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public PostResponse update(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new Exception(ErrorCode.POST_NOT_FOUND));
        User loginUser = userRepository.findById(request.userId())
                .orElseThrow(() -> new Exception(ErrorCode.USER_NOT_FOUND));
        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new Exception(ErrorCode.UPDATE_FORBIDDEN);
        }
        post.update(request.category(), request.title(), request.content());
        return PostResponse.from(post);
    }

    @Override
    @Transactional
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
