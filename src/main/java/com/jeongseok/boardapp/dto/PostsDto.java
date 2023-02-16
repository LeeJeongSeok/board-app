package com.jeongseok.boardapp.dto;

import com.jeongseok.boardapp.entity.Posts;
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
public class PostsDto {

	private Long id;
	private String title;
	private String content;
	private User user;
	private String useYn;
	private String createdAt;
	private String updatedAt;

	public static PostsDto fromEntity(Posts posts) {
		return PostsDto.builder()
			.id(posts.getId())
			.title(posts.getTitle())
			.content(posts.getContent())
			.user(posts.getUser())
			.useYn(posts.getUseYn())
			.createdAt(posts.getCreatedAt())
			.updatedAt(posts.getUpdatedAt())
			.build();
	}

}
