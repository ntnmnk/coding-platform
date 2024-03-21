package com.example.springbootproject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.springbootproject.entities.User;
import com.example.springbootproject.exception.UserAlreadyExistsException;
import com.example.springbootproject.exception.UserNotFoundException;
import com.example.springbootproject.services.UserService;


public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().userId("1").username("user1").score(50).badges(Set.of("badge1", "badge2")).build());
        users.add(User.builder().userId("2").username("user2").score(75).badges(Set.of("badge3")).build());

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());
    }

    @Test
    void testGetUserById_UserFound() throws UserNotFoundException {
        User user = User.builder().userId("1").username("user1").score(50).badges(Set.of("badge1", "badge2")).build();
        when(userService.getUserById(anyString())).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.getUserById("1");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void testGetUserById_UserNotFound() throws UserNotFoundException {
        when(userService.getUserById(anyString())).thenThrow(new UserNotFoundException(""));

        ResponseEntity<User> responseEntity = userController.getUserById("1");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testRegisterUser_Success() throws UserAlreadyExistsException {
        User user = User.builder().userId("1").username("user1").score(50).badges(Set.of("badge1", "badge2")).build();
        when(userService.registerUser(any())).thenReturn(user);

        ResponseEntity<Object> responseEntity = userController.registerUser(user);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }


}
