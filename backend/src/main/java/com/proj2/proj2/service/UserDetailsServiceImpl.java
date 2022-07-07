package com.proj2.proj2.service;

import com.proj2.proj2.model.User;
import com.proj2.proj2.repository.UserRepository;
import com.proj2.proj2.security.JwtUserDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByEmail(username);
        
        return JwtUserDetails.create(user);
    }

    public UserDetails loadUserById(Long id)
    {
        User user = userRepository.findById(id).get();
        
        return JwtUserDetails.create(user);
    }

    public UserDetails loadUserByEmail(String email) throws Exception
    {
        User user = userRepository.findByEmail(email);
        
        return JwtUserDetails.create(user);
    }
    
}
