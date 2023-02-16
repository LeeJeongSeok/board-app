package com.jeongseok.boardapp.dto;

import com.jeongseok.boardapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

	private Long id;
	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;

	public static UserDto fromEntity(User user) {
		return UserDto.builder()
			.id(user.getId())
			.username(user.getUsername())
			.password(user.getPassword())
			.name(user.getName())
			.email(user.getEmail())
			.phone(user.getPhone())
			.build();
	}

}