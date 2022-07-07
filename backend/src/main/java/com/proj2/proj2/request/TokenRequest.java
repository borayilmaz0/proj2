package com.proj2.proj2.request;

import lombok.Data;

@Data
public class TokenRequest {
    private long userId;
    private String token;
}
