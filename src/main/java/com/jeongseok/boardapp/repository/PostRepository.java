package com.jeongseok.boardapp.repository;

import com.jeongseok.boardapp.entity.Post;
import com.jeongseok.boardapp.type.PostType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllByPostType(PostType useFlag);

}
