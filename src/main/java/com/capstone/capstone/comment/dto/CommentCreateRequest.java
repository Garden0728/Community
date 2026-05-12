package com.capstone.capstone.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateRequest {

    private String content;

    private Long userId;

    private Long postId;
}