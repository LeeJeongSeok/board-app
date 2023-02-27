package com.jeongseok.boardapp.util;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class ValidationUtil {

	public static Map<String, String> validationRequestValue(Errors errors) {

		return errors.getFieldErrors().stream()
			.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
	}

}
