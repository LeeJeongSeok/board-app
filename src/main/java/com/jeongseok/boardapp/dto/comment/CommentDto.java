package com.jeongseok.boardapp.dto.comment;

import com.jeongseok.boardapp.entity.Comment;
import com.jeongseok.boardapp.entity.Post;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.type.UseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

	private Long id;
	private String comment;
	private String createdAt;
	private String updatedAt;
	private String deletedAt;
	private UseType useType;
	private User user;
	private Post post;

	public static CommentDto fromEntity(Comment comment) {
		return CommentDto.builder()
			.id(comment.getId())
			.comment(comment.getComment())
			.createdAt(comment.getCreatedAt())
			.updatedAt(comment.getUpdatedAt())
			.useType(comment.getUseType())
			.user(comment.getUser())
			.post(comment.getPost())
			.build();
	}

}
