package com.login.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.login.model.Article;



public interface ArticleRepository extends JpaRepository<Article, Long>{
	
	
	@Query("select a.nom,m.firstname,m.lastname,m.email,a.id from User m, Article a Where m = a.membre")
	List<Article> getinfo();
	
	@Query("select a.nom,a.body FROM Article a where a.id=?1")
	List<Article> getarticles(Long id);
}
