package com.login.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Commentaire implements Serializable{
	@Id	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String comm;
	@ManyToOne @JoinColumn(name="post_id",referencedColumnName="id")
	private Post post;
	@ManyToOne @JoinColumn(name="membre_id",referencedColumnName="id")
	private User membre;
	
	
	public Commentaire(String comm, Post post, User membre) {
		super();
		this.comm = comm;
		this.post = post;
		this.membre = membre;
	}
	public Commentaire() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComm() {
		return comm;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public User getMembre() {
		return membre;
	}
	public void setMembre(User membre) {
		this.membre = membre;
	}
}
