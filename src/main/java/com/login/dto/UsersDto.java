package com.login.dto;

public class UsersDto {
	
	public String password;
	public String email;
	
	 public UsersDto() {
		super();
	}
	public UsersDto( String firstname, String lastname, String status) {
		super();
	
		this.firstname = firstname;
		this.lastname = lastname;
		this.status = status;
	}
	public String firstname;
	    public String lastname;
	    public String status;

}
