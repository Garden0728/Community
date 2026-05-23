package com.capstone.capstone.exception;

public record ErrorResponse(
        String code,
        String message
) {
}
