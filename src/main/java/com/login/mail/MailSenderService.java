package com.login.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;

public class MailSenderService {

	
	
	public  JavaMailSenderImpl javaMailSender ;
 
	
	
	public MailSenderService(JavaMailSenderImpl javaMailSender)
	{
	 this.javaMailSender = javaMailSender;}
	
	
	
	
	public void send (String email, String password, boolean forgot, String queryParams) {
        MimeMessage message = javaMailSender.createMimeMessage();
        String html="<center><h3 style=\"color:#00897b\">Thank you for registering!</h3><p style=\"color: #000000\">In order to confirm the registration follow the <a href=\"http://localhost:8080/confirm" + queryParams + "\">link</a>.</p><p style=\"color: #000000\"><span style=\"color: #000000\">Sincerly, </span><a href=\"http://localhost:8082/api\">labo</a> administration</p></center>";
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,false, "utf-8");
            message.setContent(html, "text/html");
            helper.setTo(email);
           helper.setFrom("fakhric46@gmail.com");
            helper.setSubject("activation");
           // helper.setText("..........");
        } catch (MessagingException e) {
           
        } finally {}
        javaMailSender.send(message);
        
    }
	
}
