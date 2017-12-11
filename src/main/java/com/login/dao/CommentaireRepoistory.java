package com.login.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.login.model.Commentaire;

public interface CommentaireRepoistory extends JpaRepository<Commentaire, Long>{

}
