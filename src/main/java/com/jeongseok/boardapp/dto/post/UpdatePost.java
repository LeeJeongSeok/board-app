package com.jeongseok.boardapp.dto.post;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UpdatePost {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Request {

		@NotBlank
		private String title;

		@NotBlank
		private String content;
	}

}
