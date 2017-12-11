package com.login.dto;

import com.login.dto.ValidationDto;
import com.login.model.User;

public class ValidationDto {
	  public String email;
	    
	    public String id;
	    public String year;
	   

	    public static ValidationDto fromUser(User user) {
	        ValidationDto result = new ValidationDto();
	        result.id = user.getId().toString();
	        result.email = user.getEmail();
	        result.year = user.getRegistrarId().substring(0, 4);
	      
	        return result;
	    }
}
