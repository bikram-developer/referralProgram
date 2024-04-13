package com.rewards.backend.app.security.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserModel, String>{

	Optional<UserModel> findByEmail(String email);

	@Query(value="select * from user_model where user_id =:x",nativeQuery=true)
	Optional<UserModel> findByOwnerId(@Param("x")  String ownerId);

	@Query(value = "select u.role_name from user_model u where u.email =:email", nativeQuery = true)
	String getUserRoleByEmail(@Param("email") String email);
}