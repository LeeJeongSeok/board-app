package com.jeongseok.boardapp.service;

import com.jeongseok.boardapp.dto.CreatePosts;
import com.jeongseok.boardapp.dto.PostsDto;
import com.jeongseok.boardapp.dto.UpdatePosts;
import com.jeongseok.boardapp.entity.Posts;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.repository.PostsRepository;
import com.jeongseok.boardapp.repository.UserRepository;
import com.jeongseok.boardapp.type.ErrorCode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostsService {

	private final PostsRepository postsRepository;
	private final UserRepository userRepository;

	@Transactional
	public void writePosts(CreatePosts.Request request, String loginUser) {

		// 로그인한 유저 정보 가져오기
		User user = userRepository.findByUsername(loginUser).get();

		postsRepository.save(Posts.builder()
				.title(request.getTitle())
				.content(request.getContent())
				.user(user)
				.useYn("Y")
			.build());
	}

	@Transactional
	public List<PostsDto> getPostsByUseFlag() {

		List<Posts> posts = postsRepository.findAllByUseYn("Y");

		return posts.stream()
			.map(PostsDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional
	public PostsDto detailPosts(Long id) {

		Posts posts = postsRepository.findById(id).get();

		return PostsDto.builder()
			.id(posts.getId())
			.title(posts.getTitle())
			.content(posts.getContent())
			.user(posts.getUser())
			.build();
	}

	@Transactional
	public void updatePosts(Long postsId, String loginUser, UpdatePosts.Request request) {

		// 게시글 가져오기
		Posts posts = postsRepository.findById(postsId).get();

		// 유저 가져오기
		User user = userRepository.findByUsername(loginUser).get();

		if (posts.getUser().getId() == user.getId()) {
			posts.setTitle(request.getTitle());
			posts.setContent(request.getContent());

			postsRepository.save(posts);
		} else {
			throw new IllegalArgumentException(ErrorCode.USER_UN_MATCH.getDescription());
		}

	}

	@Transactional
	public void deletePosts(Long postsId, String loginUser) {

		// 게시글 가져오기
		Posts posts = postsRepository.findById(postsId).get();

		// 유저 가져오기
		User user = userRepository.findByUsername(loginUser).get();

		if (posts.getUser().getId() == user.getId()) {
			posts.setUseYn("N");
			posts.setDeletedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

			postsRepository.save(posts);
		} else {
			throw new IllegalArgumentException(ErrorCode.USER_UN_MATCH.getDescription());
		}
	}

	/**
	 * 아직 적용전 (2023.02.17)
	 * update/delete 시 현재 로그인 한 유저와 게시판을 작성한 유저를 비교하는 로직이 중복되어 별도로 빼놓은 로직
	 */
	private boolean isSameUser(Long postsId, String loginUser) {

		// 게시글 가져오기
		Posts posts = postsRepository.findById(postsId).get();

		// 유저 가져오기
		User user = userRepository.findByUsername(loginUser).get();

		if (posts.getUser().getId() == user.getId()) {
			return true;
		}
		return false;
	}
}
