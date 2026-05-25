package com.community.community.exception;

public record ErrorResponse(
        String code,
        String message
) {
}
