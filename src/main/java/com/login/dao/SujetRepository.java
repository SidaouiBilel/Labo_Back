package com.login.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.login.model.Sujet;


public interface SujetRepository extends JpaRepository<Sujet, Long>{
	
	@Query("SELECT p.title,u.firstname,u.lastname,u.status,p.id FROM Sujet s,User u,Post p WHERE p.membre = u and p.sujet = s and s.nom =?1")
	List<Sujet>  getpublications(String sujet);
	
}
