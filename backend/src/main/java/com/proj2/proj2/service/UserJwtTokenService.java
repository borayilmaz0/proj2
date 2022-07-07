package com.proj2.proj2.service;

import java.util.List;

import com.proj2.proj2.model.User;
import com.proj2.proj2.model.UserJwtToken;
import com.proj2.proj2.repository.UserJwtTokenRepository;
import com.proj2.proj2.request.TokenRequest;
import com.proj2.proj2.security.JwtTokenProvider;

import org.springframework.stereotype.Service;

@Service
public class UserJwtTokenService {
    
    private UserJwtTokenRepository userJwtTokenRepository;
    private UserService userService;

    public UserJwtTokenService(UserJwtTokenRepository userJwtTokenRepository, UserService userService)
    {
        this.userJwtTokenRepository = userJwtTokenRepository;
        this.userService = userService;
    }

    public List<UserJwtToken> findAll()
    {
        return userJwtTokenRepository.findAll();
    }

    public UserJwtToken getTokenByUserId(long userId)
    {
        if (!isUserTokenValid(userId))
            return null;
        return userJwtTokenRepository.findByUserId(userId);
    }
    
    public void deleteTokenByUserId(long userId)
    {
        UserJwtToken token = userJwtTokenRepository.findByUserId(userId);

        if (token == null)
        {
            return;
        }

        userJwtTokenRepository.delete(token);
    }

    public UserJwtToken provideToken(TokenRequest tokenUpdateRequest)
    {
        User user = userService.getUserById(tokenUpdateRequest.getUserId());

        if (user == null)
        {
            return null;
        }

        UserJwtToken token = userJwtTokenRepository.findByUserId(tokenUpdateRequest.getUserId());

        if (token == null)
        {
            token = new UserJwtToken();
            token.setUser(user);
            token.setLatestToken(tokenUpdateRequest.getToken());
            return userJwtTokenRepository.save(token);
        }

        token.setLatestToken(tokenUpdateRequest.getToken());
        return userJwtTokenRepository.save(token);
    }

    public boolean isUserTokenValid(long userId)
    {
        JwtTokenProvider tokenProvider = new JwtTokenProvider();
        return tokenProvider.isTokenValid(userJwtTokenRepository.findByUserId(userId).getLatestToken());
    }
}
