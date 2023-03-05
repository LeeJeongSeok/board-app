package com.jeongseok.boardapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionUser {

	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;

	public static SessionUser from(UserDto userDto) {
		return SessionUser.builder()
			.username(userDto.getUsername())
			.password(userDto.getPassword())
			.name(userDto.getName())
			.email(userDto.getEmail())
			.phone(userDto.getPhone())
			.build();
	}
}
