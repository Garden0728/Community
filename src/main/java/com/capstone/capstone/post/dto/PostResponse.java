package com.capstone.capstone.post.dto;

import com.capstone.capstone.post.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {

    private Long id;
    private String title;
    private String content;

    private Long userId;
    private String username;
    private String nickname;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponse from(Post post) {
        PostResponse response = new PostResponse();

        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());

        if (post.getUser() != null) {
            response.setUserId(post.getUser().getId());
            response.setUsername(post.getUser().getUsername());
            response.setNickname(post.getUser().getNickname());
        }

        return response;
    }
}