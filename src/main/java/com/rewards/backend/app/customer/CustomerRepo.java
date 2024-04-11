package com.rewards.backend.app.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

@EnableJpaRepositories
public interface CustomerRepo extends JpaRepository<Customer, Long>{

	@Query(value = """
select * from customer where email = :email
""",nativeQuery = true)
	String getPassword(@Param("email")String email);
	
@Query(value = """
s
""",nativeQuery = true)
	int countNoOfActiveEmails throws MultipleCreatedPersonToDeal;
}
