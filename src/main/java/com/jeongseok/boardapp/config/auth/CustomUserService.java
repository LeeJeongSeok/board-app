package com.jeongseok.boardapp.config.auth;

import com.jeongseok.boardapp.dto.user.CreateUser;
import com.jeongseok.boardapp.dto.user.LoginUserDto;
import com.jeongseok.boardapp.dto.user.UpdateUser;
import com.jeongseok.boardapp.dto.user.UserDto;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.exception.UserException;
import com.jeongseok.boardapp.repository.UserRepository;
import com.jeongseok.boardapp.type.ErrorCode;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final HttpSession httpSession;

	// 시큐리티에서 별도로 관리하는 메소드
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = getUser(username);

		httpSession.setAttribute("user", CreateUser.Response.from(UserDto.fromEntity(user)));

		return new LoginUserDto(user.getUsername(), user.getPassword());
	}

	@Transactional
	public void joinUser(CreateUser.Request createUser) {

		// 회원가입시 중복 아이디 체크
		if (isExists(createUser.getUsername())) {
			throw new IllegalArgumentException(ErrorCode.USER_ALREADY_EXISTS.getDescription());
		}

		createUser.setPassword(bCryptPasswordEncoder.encode(createUser.getPassword()));

		userRepository.save(User.builder()
			.username(createUser.getUsername())
			.password(createUser.getPassword())
			.name(createUser.getName())
			.email(createUser.getEmail())
			.phone(createUser.getPhone())
			.build());
	}

	@Transactional
	public UserDto findUserByUsername(String username) {
		User user = getUser(username);

		return UserDto.fromEntity(user);
	}


	@Transactional
	public void updateUser(UpdateUser.Request updateUser, String username) {
		// 로그인한 유저와 디비에 저장되어 있는 유저와 일치하는지 판별
		User user = getUser(username);

		// 업데이트할 내용 선언
		user.update(bCryptPasswordEncoder.encode(updateUser.getPassword()), updateUser.getName(),
			updateUser.getEmail(), updateUser.getPhone());

		// Repository에 저장
		userRepository.save(user);
	}

	private boolean isExists(String username) throws UserException {
		return userRepository.existsUserByUsername(username);
	}

	private User getUser(String username) throws UserException {
		// 로그인한 유저와 디비에 저장되어 있는 유저와 일치하는지 판별
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));
		return user;
	}
}