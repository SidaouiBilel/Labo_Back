package com.login.model;

import javax.persistence.*;

@Entity	
public class AdminEmails {
	@Id @GeneratedValue
	private int id;
	private String email;
	public AdminEmails(String email) {
		super();
		this.email = email;
	}
	
	public AdminEmails() {
		super();
	}

}
