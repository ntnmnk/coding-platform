package com.example.springbootproject.services;

import java.util.List;

import com.example.springbootproject.entities.User;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(String userId);
    User registerUser(User user);
    User updateScore(String userId, int score);
    void deregisterUser(String userId);  
    public boolean exists(String userId);
}
