package com.login.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Message implements Serializable{
	@Id @GeneratedValue
	private Long id;
	private String msg;
	
	@ManyToOne @JoinColumn(name="sender_id",referencedColumnName="id")
	private User sender;
	@ManyToOne @JoinColumn(name="receiver_id",referencedColumnName="id")
	private User receiver;
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public User getSender() {
		return sender;
	}
	public Long getId() {
		return id;
	}
	public String getMsg() {
		return msg;
	}
	public User getReceiver() {
		return receiver;
	}
	public Message(Long id, String msg, User sender, User receiver) {
		super();
		this.id = id;
		this.msg = msg;
		this.sender = sender;
		this.receiver = receiver;
	}
	public Message() {
		super();
	}
}
