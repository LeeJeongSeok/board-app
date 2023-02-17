package com.jeongseok.boardapp.dto;

import com.jeongseok.boardapp.entity.User;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CreatePosts {

	@Getter @Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Request {

		@NotBlank
		private String title;

		@NotBlank
		private String content;

	}


	@Getter @Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {

		private String title;
		private String content;
		private User user;
		private String createdAt;


	}

}
