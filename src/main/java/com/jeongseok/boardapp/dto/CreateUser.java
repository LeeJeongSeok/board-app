package com.jeongseok.boardapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CreateUser {

	@Getter @Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Request {

		@NotBlank
		private String username;

		@NotBlank
		@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용해주세요.")
		private String password;

		@NotBlank
		@Size(min = 2, max = 6, message = "이름은 2~6자 사이로 입력해주세요.")
		private String name;

		@NotBlank
		@Email
		private String email;
		@NotBlank
		private String phone;
	}

	@Getter @Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {

		private String username;
		private String password;
		private String name;
		private String email;
		private String phone;

		public static Response from(UserDto userDto) {
			return Response.builder()
				.username(userDto.getUsername())
				.password(userDto.getPassword())
				.name(userDto.getName())
				.email(userDto.getEmail())
				.phone(userDto.getPhone())
				.build();
		}
	}


}
