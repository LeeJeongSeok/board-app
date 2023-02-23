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

	public CommentInfo(CommentDto commentDto) {
		this.id = commentDto.getId();
		this.comment = commentDto.getComment();
		this.createdAt = commentDto.getCreatedAt();
		this.updatedAt = commentDto.getUpdatedAt();
		this.deletedAt = commentDto.getDeletedAt();
		this.useType = commentDto.getUseType();
		this.post = commentDto.getPost();
		this.user = commentDto.getUser();
	}
}
