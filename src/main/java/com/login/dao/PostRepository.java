package com.login.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.login.model.Commentaire;
import com.login.model.Post;

public interface PostRepository extends JpaRepository<Post,Long>{

		@Query("SELECT p.title,p.body,u.firstname,u.lastname FROM Post p, User u WHERE p.membre = u and p.id = ?1 ")
		List<Post> getpost(Long id);
		@Query("Select c.comm,u.firstname,u.lastname From Commentaire c,Post p,User u WHERE c.membre = u and c.post = p and p.id = ?1")
		List<Post> getcomments(Long id);
}
