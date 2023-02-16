package com.jeongseok.boardapp.dto;

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
public class PostsInfo {

	private Long id;
	private String title;
	private String content;
	private User user;
	private String createdAt;
}
