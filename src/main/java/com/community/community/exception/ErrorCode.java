    package com.community.community.exception;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import org.springframework.http.HttpStatus;

    @Getter
    @AllArgsConstructor
    public enum ErrorCode {
        // User
        USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원이 없습니다."),
        LOGIN_ID_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
        NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
        PHONE_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 등록된 전화번호입니다."),
        LOGIN_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디가 존재하지 않습니다."),
        PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),
        PASSWORD_CONFIRM_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
        CURRENT_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다."),
        NEW_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "새 비밀번호가 일치하지 않습니다."),
        USER_NOT_VERIFIED(HttpStatus.NOT_FOUND, "아이디 또는 전화번호가 일치하지 않습니다."),
        USER_MATCH_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 회원이 없습니다."),

        // Post
        POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 없습니다."),

        // Comment
        COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 없습니다."),

        // Common
        UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN, "수정 권한이 없습니다."),
        DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        private final HttpStatus status;
        private final String message;
    }
