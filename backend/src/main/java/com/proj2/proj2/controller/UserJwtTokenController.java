package com.proj2.proj2.controller;

import java.util.List;

import com.proj2.proj2.model.UserJwtToken;
import com.proj2.proj2.request.TokenRequest;
import com.proj2.proj2.service.UserJwtTokenService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
@CrossOrigin(origins = "http://localhost:4200")
public class UserJwtTokenController {
    
    private UserJwtTokenService userJwtTokenService;

    public UserJwtTokenController(UserJwtTokenService userJwtTokenService)
    {
        this.userJwtTokenService = userJwtTokenService;
    }

    @GetMapping
    public List<UserJwtToken> getAll()
    {
        return userJwtTokenService.findAll();
    }

    @GetMapping("/{userId}")
    public UserJwtToken getToken(@PathVariable long userId)
    {
        return userJwtTokenService.getTokenByUserId(userId);
    }

    @PostMapping
    public UserJwtToken postToken(@RequestBody TokenRequest tokenRequest)
    {
        return userJwtTokenService.provideToken(tokenRequest);
    }

    @GetMapping("/validate/{userId}")
    public boolean isUserTokenValid(@PathVariable long userId)
    {
        return userJwtTokenService.isUserTokenValid(userId);
    }
}
