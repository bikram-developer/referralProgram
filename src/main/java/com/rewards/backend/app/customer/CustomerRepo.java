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
String getCustomerDetails(@Param("email")String email);
	
@Query(value = """
select count(id) from customer where email = :email
""",nativeQuery = true)
int countNoOfActiveEmails(@Param("email")String email) ;

//@Query(value = """
//select count(id) from customer where mobile_number = :number
//""",nativeQuery = true)
//int countNoOfNumberPresent(@Param("number")String number) ;

@Query(value = """
select password from customer where email = :email
""",nativeQuery = true)
String getPassword(@Param("email")String email) ;

@Query(value="""
SELECT * FROM customer WHERE email = :email AND password = :password
""",nativeQuery = true)
Customer getCustomerFromDbIfTrue(@Param("email") String email,@Param("password") String password);

@Query(value="""
SELECT is_active FROM customer WHERE email = :email 
""",nativeQuery = true)
boolean CustomerStatus(@Param("email")String email);

@Query(value="""
SELECT is_locked FROM customer WHERE email = :email 
""",nativeQuery = true)
boolean loginStatus(@Param("email")String email);

@Query("SELECT c FROM Customer c WHERE c.referralCode = :referralCode")
Customer findByReferralCode(@Param("referralCode") String referralCode);

@Query("SELECT COUNT(c) FROM Customer c WHERE c.referrerId = :referrerId")
int countReferralsByReferrerId(@Param("referrerId") Long referrerId);
}