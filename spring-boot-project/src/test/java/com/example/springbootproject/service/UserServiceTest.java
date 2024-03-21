package com.example.springbootproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.springbootproject.entities.User;
import com.example.springbootproject.exception.UserAlreadyExistsException;
import com.example.springbootproject.exception.UserNotFoundException;
import com.example.springbootproject.reposiotries.UserRepository;
import com.example.springbootproject.services.UserServiceImpl;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        User user = User.builder()
                        .userId("1")
                        .username("user1")
                        .score(50)
                        .badges(Collections.emptySet())
                        .build();
        when(userRepository.findById("1")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.registerUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository).findById("1");
        verify(userRepository).findByUsername("user1");
        verify(userRepository).save(user);
    }

    @Test
    void testRegisterUser_UserIdAlreadyExists() {
        // Arrange
        User user = User.builder()
                        .userId("1")
                        .username("user1")
                        .score(50)
                        .badges(Collections.emptySet())
                        .build();
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(user));
        verify(userRepository).findById("1");
        verify(userRepository, never()).findByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        // Arrange
        User user = User.builder()
                        .userId("1")
                        .username("user1")
                        .score(50)
                        .badges(Collections.emptySet())
                        .build();
        when(userRepository.findById("1")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(user));
        verify(userRepository).findById("1");
        verify(userRepository).findByUsername("user1");
        verify(userRepository, never()).save(any(User.class));
    }

        @Test
    void testGetUserById_UserNotFound() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("1"));
        verify(userRepository).findById("1");
    }

}
