package com.proj2.proj2.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private long userId;
    private String token;
}
