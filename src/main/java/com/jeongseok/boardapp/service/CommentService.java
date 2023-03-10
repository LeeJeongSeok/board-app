package com.jeongseok.boardapp.service;


import com.jeongseok.boardapp.dto.comment.CreateComment;
import com.jeongseok.boardapp.dto.comment.UpdateComment;
import com.jeongseok.boardapp.entity.Comment;
import com.jeongseok.boardapp.entity.Post;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.repository.CommentRepository;
import com.jeongseok.boardapp.repository.PostRepository;
import com.jeongseok.boardapp.repository.UserRepository;
import com.jeongseok.boardapp.type.ErrorCode;
import com.jeongseok.boardapp.type.UseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public void writeComment(long postId, CreateComment.Request request, String loginUser) {

		// 로그인한 유저 정보 가져오기
		User user = userRepository.findByUsername(loginUser)
			.orElseThrow(
				() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));

		// 게시글 정보 가져오기
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		commentRepository.save(Comment.builder()
			.comment(request.getComment())
			.post(post)
			.user(user)
			.useType(UseType.Y)
			.build());
	}


	@Transactional
	public void updateComment(long commentId, UpdateComment.Request request, String loginUser) {

		// 댓글 가져오기
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

		if (!comment.isSameWriter(loginUser)) {
			throw new IllegalArgumentException(ErrorCode.USER_UN_MATCH.getDescription());
		}

		comment.update(request.getComment());
		commentRepository.save(comment);
	}

	@Transactional
	public void deleteComment(long commentId, String loginUser) {

		// 댓글 가져오기
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

		if (!comment.isSameWriter(loginUser)) {
			throw new IllegalArgumentException(ErrorCode.USER_UN_MATCH.getDescription());
		}

		comment.delete(UseType.N);
		commentRepository.save(comment);

	}
}
