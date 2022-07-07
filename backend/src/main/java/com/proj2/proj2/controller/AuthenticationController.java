package com.proj2.proj2.controller;

import com.proj2.proj2.model.User;
import com.proj2.proj2.request.TokenRequest;
import com.proj2.proj2.request.UserLoginRequest;
import com.proj2.proj2.request.UserRegisterRequest;
import com.proj2.proj2.response.LoginResponse;
import com.proj2.proj2.security.JwtTokenProvider;
import com.proj2.proj2.service.UserJwtTokenService;
import com.proj2.proj2.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private UserJwtTokenService userJwtTokenService;
    private PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, 
    		PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
            UserJwtTokenService userJwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userJwtTokenService = userJwtTokenService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserLoginRequest loginRequest)
    {
        System.out.println("authenticating...");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication auth = authenticationManager.authenticate(authToken);
        System.out.println("authenticated");

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.genereteJwtToken(auth);

        TokenRequest tokenRequest = new TokenRequest();
        long userId = jwtTokenProvider.getUserIdFromJwt(jwtToken);
        tokenRequest.setUserId(userId);
        tokenRequest.setToken(jwtToken);
        userJwtTokenService.provideToken(tokenRequest);

        return new LoginResponse(userId, "Bearer " + jwtToken);
    }

    @PostMapping("/register")
    public HttpStatus register(@RequestBody UserRegisterRequest registerRequest)
    {
        System.out.println(registerRequest);

        if (userService.getUserByEmail(registerRequest.getEmail()) != null)
        {
            return HttpStatus.BAD_REQUEST;
        }
        else
        {
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            userService.addUser(user);

            return HttpStatus.CREATED;
        }
    }
    
}
