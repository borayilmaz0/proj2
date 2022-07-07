package com.proj2.proj2.controller;

import java.util.List;

import com.proj2.proj2.model.User;
import com.proj2.proj2.service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) 
    {
        this.userService = userService;
    }

    @GetMapping
    public List<User>getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/idIsNot/{userId}")
    public List<User> getAllUsersOtherThan(@PathVariable long userId)
    {
        return userService.getAllUsersOtherThan(userId);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId)
    {
        return userService.getUserById(userId);
    }

    @GetMapping("/getByMail")
    public User getUserByEmail(@RequestBody String email)
    {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public User addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, 
        @RequestBody User newUser)
    {
        return userService.updateUserById(userId, newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId)
    {
        userService.deleteUserById(userId);
    }

}
