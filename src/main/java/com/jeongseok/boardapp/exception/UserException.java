package com.jeongseok.boardapp.exception;

import com.jeongseok.boardapp.type.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserException extends RuntimeException {

	private ErrorCode errorCode;
	private String errorMessage;

	public UserException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}

}
