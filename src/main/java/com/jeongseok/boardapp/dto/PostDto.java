package com.jeongseok.boardapp.dto;

import com.jeongseok.boardapp.entity.Post;
import com.jeongseok.boardapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

	private Long id;
	private String title;
	private String content;
	private User user;
	private String useYn;
	private String createdAt;
	private String updatedAt;

	public static PostDto fromEntity(Post post) {
		return PostDto.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.user(post.getUser())
			.useYn(post.getUseYn())
			.createdAt(post.getCreatedAt())
			.updatedAt(post.getUpdatedAt())
			.build();
	}

}
