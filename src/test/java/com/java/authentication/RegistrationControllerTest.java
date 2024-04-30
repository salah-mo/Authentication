package com.java.authentication;

import com.java.authentication.controller.RegistrationController;
import com.java.authentication.model.User;
import com.java.authentication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private UserService userService;

    private User validUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        validUser = new User("validUser", "validPassword");
    }

    @Nested
    class WhenRegisteringUser {

        @Test
        @DisplayName("Should return OK response when valid user is registered")
        public void shouldReturnOkResponseWhenValidUserIsRegistered() {
            doNothing().when(userService).save(any());

            ResponseEntity<?> response = registrationController.registerUser(validUser);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals("User registered successfully!", response.getBody());
        }

        @Test
        @DisplayName("Should throw exception when UserService save method throws exception")
        public void shouldThrowExceptionWhenUserServiceSaveThrowsException() {
            doThrow(new RuntimeException("Unexpected error")).when(userService).save(validUser);

            Exception exception = assertThrows(RuntimeException.class, () -> registrationController.registerUser(validUser));

            assertEquals("Unexpected error", exception.getMessage());
        }

        @Test
        @DisplayName("Should return error response when UserService save method fails")
        public void shouldReturnErrorResponseWhenUserServiceSaveFails() {
            doThrow(new IllegalArgumentException("Username already taken")).when(userService).save(validUser);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> registrationController.registerUser(validUser));

            assertEquals("Username already taken", exception.getMessage());
        }


    }

    @Nested
    class WhenValidatingUser {

        @Test
        @DisplayName("Should throw exception when null user is provided")
        public void shouldThrowExceptionWhenNullUserIsProvided() {
            doThrow(new IllegalArgumentException("User cannot be null")).when(userService).save(null);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> registrationController.registerUser(null));

            assertEquals("User cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when user with empty username is provided")
        public void shouldThrowExceptionWhenUserWithEmptyUsernameIsProvided() {
            User userWithEmptyUsername = new User("", "validPassword");
            doThrow(new IllegalArgumentException("Username cannot be empty")).when(userService).save(any());

            Exception exception = assertThrows(IllegalArgumentException.class, () -> registrationController.registerUser(userWithEmptyUsername));

            assertEquals("Username cannot be empty", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when user with empty password is provided")
        public void shouldThrowExceptionWhenUserWithEmptyPasswordIsProvided() {
            User userWithEmptyPassword = new User("validUser", "");
            doThrow(new IllegalArgumentException("Password cannot be empty")).when(userService).save(any());

            Exception exception = assertThrows(IllegalArgumentException.class, () -> registrationController.registerUser(userWithEmptyPassword));

            assertEquals("Password cannot be empty", exception.getMessage());
        }
    }
}