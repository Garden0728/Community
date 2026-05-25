package com.community.community.entity.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    EMPLOYMENT("취업"),
    STUDY("공부"),
    CONCERN("고민"),
    FREE("자유");

    private final String displayName;
}
