package com.jeongseok.boardapp.repository;

import com.jeongseok.boardapp.entity.Posts;
import com.jeongseok.boardapp.type.PostsType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {

	List<Posts> findAllByUseYn(String useFlag);

}
