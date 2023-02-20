package com.jeongseok.boardapp.dto;

import com.jeongseok.boardapp.entity.Comment;
import com.jeongseok.boardapp.entity.Post;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.type.CommentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

	private Long id;
	private String comment;
	private String createdAt;
	private String updatedAt;
	private String deletedAt;
	private CommentType commentType;
	private User user;
	private Post post;

	public static CommentDto fromEntity(Comment comment) {
		return CommentDto.builder()
			.id(comment.getId())
			.comment(comment.getComment())
			.createdAt(comment.getCreatedAt())
			.updatedAt(comment.getUpdatedAt())
			.commentType(comment.getCommentType())
			.user(comment.getUser())
			.post(comment.getPost())
			.build();
	}

}
