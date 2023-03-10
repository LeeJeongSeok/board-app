package com.jeongseok.boardapp.repository;

import com.jeongseok.boardapp.entity.Post;
import com.jeongseok.boardapp.type.UseType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllByUseType(UseType useFlag);

}
