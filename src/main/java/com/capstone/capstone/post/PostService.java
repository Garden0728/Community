package com.capstone.capstone.post;

import com.capstone.capstone.post.dto.PostCreateRequest;
import com.capstone.capstone.post.dto.PostResponse;
import com.capstone.capstone.post.dto.PostUpdateRequest;
import com.capstone.capstone.user.User;
import com.capstone.capstone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 생성
    public PostResponse save(PostCreateRequest request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        return PostResponse.from(savedPost);
    }

    // 게시글 전체 조회
    public List<PostResponse> findAll() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    // 특정 유저 게시글 조회
    public List<PostResponse> findByUserId(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    // 게시글 상세 조회
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        return PostResponse.from(post);
    }

    // 게시글 수정
    public PostResponse update(Long id, PostUpdateRequest request, Long loginUserId) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        User loginUser = userRepository.findById(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        if (!post.getUser().getId().equals(loginUser.getId()) && loginUser.getGrade() != 1) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        Post updatedPost = postRepository.save(post);

        return PostResponse.from(updatedPost);
    }

    // 게시글 삭제
    public void delete(Long id, Long loginUserId) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        User loginUser = userRepository.findById(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        if (!post.getUser().getId().equals(loginUser.getId()) && loginUser.getGrade() != 1) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }


}
