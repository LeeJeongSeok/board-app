package com.jeongseok.boardapp.repository;

import com.jeongseok.boardapp.entity.Comment;
import com.jeongseok.boardapp.type.CommentType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPostIdAndCommentType(long postId, CommentType commentType);
}
