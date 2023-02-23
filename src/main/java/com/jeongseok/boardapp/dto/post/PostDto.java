package com.jeongseok.boardapp.dto.post;

import com.jeongseok.boardapp.entity.Comment;
import com.jeongseok.boardapp.entity.Post;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.type.UseType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

	private Long id;
	private String title;
	private String content;
	private User user;
	private UseType useYn;
	private String createdAt;
	private String updatedAt;
	private List<Comment> comments;

	public static PostDto fromEntity(Post post) {
		return PostDto.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.user(post.getUser())
			.useYn(post.getUseType())
			.createdAt(post.getCreatedAt())
			.updatedAt(post.getUpdatedAt())
			.comments(post.getComments())
			.build();
	}

}
