package com.jeongseok.boardapp.service;


import com.jeongseok.boardapp.dto.comment.CommentDto;
import com.jeongseok.boardapp.dto.comment.CreateCommentDto;
import com.jeongseok.boardapp.entity.Comment;
import com.jeongseok.boardapp.entity.Post;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.repository.CommentRepository;
import com.jeongseok.boardapp.repository.PostRepository;
import com.jeongseok.boardapp.repository.UserRepository;
import com.jeongseok.boardapp.type.CommentType;
import com.jeongseok.boardapp.type.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	public List<CommentDto> getCommentsByCommentType(Long postId) {
		List<Comment> comments = commentRepository.findByPostIdAndCommentType(postId, CommentType.Y);

		return comments.stream()
			.map(CommentDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional
	public void writeComment(Long postId, String comment, String loginUser) {

		// 로그인한 유저 정보 가져오기
		User user = userRepository.findByUsername(loginUser)
			.orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));

		// 게시글 정보 가져오기
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));


		commentRepository.save(Comment.builder()
				.comment(comment)
				.post(post)
				.user(user)
				.commentType(CommentType.Y)
			.build());
	}


	@Transactional
	public void updateComment(Long postId, Long commentId, String newComment, String loginUser) {

		// 댓글 가져오기
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

		if (comment.isSameWriter(loginUser)) {
			comment.update(newComment);
			commentRepository.save(comment);
		} else {
			throw new IllegalArgumentException(ErrorCode.USER_UN_MATCH.getDescription());
		}
	}

	@Transactional
	public void deleteComment(Long postId, Long commentId, String loginUser) {

		// 댓글 가져오기
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

		if (comment.isSameWriter(loginUser)) {
			comment.delete(CommentType.N);
			commentRepository.save(comment);
		} else {
			throw new IllegalArgumentException(ErrorCode.USER_UN_MATCH.getDescription());
		}

	}
}
