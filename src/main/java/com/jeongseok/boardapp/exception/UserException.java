package com.jeongseok.boardapp.exception;

import com.jeongseok.boardapp.type.ErrorCode;
import lombok.AllArgsConstructor;

/**
 * 개발 히스토리 내역 (2023.02.17)
 * 유저와 관련된 Custom Exception 클래스를 생성했으나, 호출 시 ErrorCode 값이 null로 출력되어 현재 사용하지 않음
 * 현 상황 : IllegalArgumentException 으로 유저와 관련된 예외 처리중
 */
@AllArgsConstructor
public class UserException extends RuntimeException {

	private ErrorCode errorCode;
	private String errorMessage;

	public UserException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}

}
