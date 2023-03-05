package com.jeongseok.boardapp.service;

import com.jeongseok.boardapp.dto.comment.CommentDto;
import com.jeongseok.boardapp.dto.post.CreatePost;
import com.jeongseok.boardapp.dto.post.PostDto;
import com.jeongseok.boardapp.dto.post.UpdatePost;
import com.jeongseok.boardapp.entity.Post;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.repository.PostRepository;
import com.jeongseok.boardapp.repository.UserRepository;
import com.jeongseok.boardapp.type.ErrorCode;
import com.jeongseok.boardapp.type.UseType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Transactional
	public void writePost(CreatePost.Request request, String userName) {

		User user = userRepository.findByUsername(userName).get();

		postRepository.save(Post.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.user(user)
			.useType(UseType.Y)
			.build());
	}

	@Transactional
	public List<PostDto> getPostsByPostType() {

		List<Post> posts = postRepository.findAllByUseType(UseType.Y);

		return posts.stream()
			.map(PostDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional
	public PostDto detailPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		return PostDto.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.comments(post.getComments().stream().map(CommentDto::fromEntity).collect(Collectors.toList()))
			.user(post.getUser())
			.build();
	}

	@Transactional
	public void updatePost(Long postsId, String loginUser, UpdatePost.Request request) {

		// 게시글 가져오기
		Post post = postRepository.findById(postsId).get();

		if (post.isSameWriter(loginUser)) {
			post.update(request.getTitle(), request.getContent());
			postRepository.save(post);
		} else {
			throw new IllegalArgumentException(ErrorCode.USER_UN_MATCH.getDescription());
		}

	}

	@Transactional
	public void deletePost(Long postsId, String loginUser) {

		// 게시글 가져오기
		Post post = postRepository.findById(postsId).get();

		if (post.isSameWriter(loginUser)) {
			post.delete(UseType.N);
			postRepository.save(post);
		} else {
			throw new IllegalArgumentException(ErrorCode.USER_UN_MATCH.getDescription());
		}
	}
}
