package com.login.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.login.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
	
	@Query("SELECT m.msg,u2.firstname,u2.lastname,u1.id FROM Message m,User u1,User u2 WHERE m.receiver = u1 and u1.email=?1 and u2.id = m.sender")
	List<Message> getmsgs(String email);

}
