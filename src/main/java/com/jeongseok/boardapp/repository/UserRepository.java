package com.jeongseok.boardapp.repository;

import com.jeongseok.boardapp.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	boolean existsUserByUsername(String username);

	/* OAuth */
	Optional<User> findByEmail(String email);


}
