package com.jeongseok.boardapp.entity;


import com.jeongseok.boardapp.type.UseType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post extends BaseEntity{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private long id;

	private String title;
	private String content;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	private String deletedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "use_yn")
	private UseType useType;

	@OneToMany(mappedBy = "post")
	@Where(clause = "use_yn = 'Y'")
	@OrderBy("id ASC")
	private List<Comment> comments;


	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public void delete(UseType useType) {
		this.useType = useType;
		this.deletedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
	}

	public boolean isSameWriter(String loginUser) {
		return this.user.getUsername().equals(loginUser);
	}

}
