package com.capstone.capstone.comment.dto;

import com.capstone.capstone.comment.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {

    private Long id;

    private String content;

    private Long userId;
    private String username;
    private String nickname;

    private Long postId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponse from(Comment comment) {

        CommentResponse response = new CommentResponse();

        response.setId(comment.getId());
        response.setContent(comment.getContent());

        response.setUserId(comment.getUser().getId());
        response.setUsername(comment.getUser().getUsername());
        response.setNickname(comment.getUser().getNickname());

        response.setPostId(comment.getPost().getId());

        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(comment.getUpdatedAt());

        return response;
    }
}