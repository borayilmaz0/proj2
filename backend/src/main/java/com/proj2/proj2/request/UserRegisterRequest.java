package com.proj2.proj2.request;

import lombok.Data;

@Data
public class UserRegisterRequest {
    
    private String username;
    private String email;
    private String password;
}
