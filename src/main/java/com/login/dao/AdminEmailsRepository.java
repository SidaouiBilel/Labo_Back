package com.login.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.login.model.AdminEmails;
import com.login.model.User;

public interface AdminEmailsRepository extends JpaRepository<AdminEmails, Long> {

	AdminEmails findByEmail(String email);
}
