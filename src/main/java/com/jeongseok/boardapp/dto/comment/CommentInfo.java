package com.jeongseok.boardapp.dto.comment;

import com.jeongseok.boardapp.entity.Comment;
import com.jeongseok.boardapp.entity.Post;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.type.UseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentInfo {
	private Long id;

	private String comment;

	private String createdAt;
	private String updatedAt;
	private String deletedAt;

	private UseType useType;

	private Post post;

	private User user;

	public CommentInfo(Comment comment) {
		this.id = comment.getId();
		this.comment = comment.getComment();
		this.createdAt = comment.getCreatedAt();
		this.updatedAt = comment.getUpdatedAt();
		this.deletedAt = comment.getDeletedAt();
		this.useType = comment.getUseType();
		this.post = comment.getPost();
		this.user = comment.getUser();
	}
}
