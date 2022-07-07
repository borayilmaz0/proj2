package com.proj2.proj2.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
