package com.jeongseok.boardapp.dto.post;

import com.jeongseok.boardapp.dto.comment.CommentInfo;
import com.jeongseok.boardapp.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostInfo {

	private Long id;
	private String title;
	private String content;
	private User user;
	private String createdAt;

	private List<CommentInfo> comments;

	// dto -> response
	public PostInfo(PostDto postDto) {
		this.id = postDto.getId();
		this.title = postDto.getTitle();
		this.content = postDto.getContent();
		this.user = postDto.getUser();
		this.createdAt = postDto.getCreatedAt();
		this.comments = postDto.getComments().stream().map(CommentInfo::new).collect(Collectors.toList());
	}


}
