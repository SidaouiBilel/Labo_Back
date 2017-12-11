package com.login.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.login.model.User;

public interface UserRepositories extends JpaRepository<User, Long>{
	@Query("SELECT m.firstname, m.lastname, m.email FROM User m WHERE m.status = ?1")
	List<User> getmember(String status);
	
	@Query("SELECT m.firstname, m.lastname, m.status,m.email,m.id FROM User m  WHERE m.email = ?1")
	List<User> getinformation(String email);
}
