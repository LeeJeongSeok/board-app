package com.jeongseok.boardapp.exception;

import com.jeongseok.boardapp.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserException extends RuntimeException {

	private ErrorCode errorCode;
	private String errorMessage;

	public UserException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}

}
