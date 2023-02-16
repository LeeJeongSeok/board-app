package com.jeongseok.boardapp.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	USER_NOT_FOUND("사용자가 없습니다."),

	USER_ALREADY_EXISTS("사용자가 이미 존재합니다."),

	USER_UN_MATCH("현재 로그인 사용자와 게시글 작성자가 다릅니다."),

	USER_NOT_LOGIN("로그인 후 사용해주세요"),
	;

	private final String description;
}
