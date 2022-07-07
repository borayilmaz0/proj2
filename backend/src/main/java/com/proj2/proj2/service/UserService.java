package com.proj2.proj2.service;

import java.util.List;
import java.util.Optional;

import com.proj2.proj2.model.User;
import com.proj2.proj2.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public List<User> getAllUsersOtherThan(long userId)
    {
        return userRepository.findAllByIdIsNot(userId);
    }

    // public List<User> getAllInvitedUsersFromEventId(long eventId)
    // {
    //     List<User> result = new ArrayList<>();
    //     List<Mail> mails = mailRepository.findAll();
    //     if (mails.isEmpty())
    //     {
    //         return result;
    //     }

    //     for (Mail m : mails)
    //     {
    //         if (m.getEvent().getId() == eventId)
    //             result.add(m.getInvitedUser());
    //     }
    //     return result;
    // }

    public User getUserById(long userId)
    {
        return userRepository.findById(userId).orElse(null);
    }

    public User getUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public User getUserByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public User addUser(User user)
    {
        User userWithSameEmail = userRepository.findByEmail(user.getEmail());
        if (userWithSameEmail != null)
            return null;
        else
            return userRepository.save(user);
    }

    public User updateUserById(long userId, User newUser)
    {
        Optional<User> oldUser = userRepository.findById(userId);

        if (oldUser.isEmpty())
        {
            return null;
        }
        else
        {
            User foundUser = oldUser.get();
            foundUser.setUsername(newUser.getUsername());
            foundUser.setPassword(newUser.getPassword());

            userRepository.save(foundUser);

            return foundUser;
        }
    }

    public void deleteUserById(long userId)
    {
        userRepository.deleteById(userId);
    }
}
