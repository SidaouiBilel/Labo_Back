package com.login.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Post implements Serializable{
	@Id	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String body;
	@ManyToOne 
	private User membre;
	@ManyToOne
	private Sujet sujet;
	@OneToMany(mappedBy="post",fetch=FetchType.LAZY)
	private Collection<Commentaire> commentaires;
	
	
	public Post(String title, String body, User membre) {
		super();
		this.title = title;
		this.body = body;
		this.membre = membre;
		
	}
	public Sujet getSujet() {
		return sujet;
	}
	public void setSujet(Sujet sujet) {
		this.sujet = sujet;
	}
	public User getMembre() {
		return membre;
	}
	public void setMembre(User membre) {
		this.membre = membre;
	}
	
	public Post() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	

}
