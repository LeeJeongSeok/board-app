package com.jeongseok.boardapp.service;


import com.jeongseok.boardapp.dto.user.CreateUser;
import com.jeongseok.boardapp.dto.user.UpdateUser;
import com.jeongseok.boardapp.dto.user.UserDto;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.exception.UserException;
import com.jeongseok.boardapp.repository.UserRepository;
import com.jeongseok.boardapp.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));

		return UserDto.fromEntity(user);
	}


	@Transactional
	public void updateUser(UpdateUser.Request updateUser, String username) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));

		// 업데이트
		user.update(bCryptPasswordEncoder.encode(updateUser.getPassword()), updateUser.getName(),
			updateUser.getEmail(), updateUser.getPhone());

		// Repository에 저장
		userRepository.save(user);
	}

	private boolean isExists(String username) throws UserException {
		return userRepository.existsUserByUsername(username);
	}

}
