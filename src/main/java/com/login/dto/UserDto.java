package com.login.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.login.model.User;

public class UserDto {

    public Long id;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String email;

   
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String year;

    public UserDto(User user, boolean full) {
        this.id = user.getId();
        if (full) {
            this.email = user.getEmail();
            
            this.year = user.getRegistrarId().substring(0, 4);
        }
    }
}