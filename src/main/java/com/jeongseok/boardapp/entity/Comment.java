package com.jeongseok.boardapp.entity;

import com.jeongseok.boardapp.type.CommentType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	private String comment;

	private String deletedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "use_yn")
	private CommentType commentType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public void update(String comment) {
		this.comment = comment;
	}

	public void delete(CommentType commentType) {
		this.commentType = commentType;
		this.deletedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
	}

	public boolean isSameWriter(String loginUser) {
		return this.user.getUsername().equals(loginUser);
	}



}
