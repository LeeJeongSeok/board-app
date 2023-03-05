package com.jeongseok.boardapp.config.auth;

import com.jeongseok.boardapp.dto.user.CreateUser;
import com.jeongseok.boardapp.dto.user.UserDto;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.repository.UserRepository;
import com.jeongseok.boardapp.type.ErrorCode;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final HttpSession httpSession;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));

		httpSession.setAttribute("user", CreateUser.Response.from(UserDto.fromEntity(user)));

		return new CustomUserDetails(user.getUsername(), user.getPassword());
	}
}
