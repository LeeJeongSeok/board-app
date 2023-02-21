package com.jeongseok.boardapp.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdateUser {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Request {
		private String username;
		@NotBlank
		@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용해주세요.")
		private String password;

		@NotBlank
		@Size(min = 2, max = 6, message = "이름은 2~6자 사이로 입력해주세요.")
		private String name;

		@NotBlank
		@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
		private String email;
		@NotBlank
		private String phone;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {
		private String username;
		private String password;
		private String name;
		private String email;
		private String phone;

		public static UpdateUser.Response from(UserDto userDto) {
			return UpdateUser.Response.builder()
				.username(userDto.getUsername())
				.password(userDto.getPassword())
				.name(userDto.getName())
				.email(userDto.getEmail())
				.phone(userDto.getPhone())
				.build();
		}
	}
}
