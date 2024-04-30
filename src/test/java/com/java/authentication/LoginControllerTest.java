package com.java.authentication;

import com.java.authentication.controller.AuthResponse;
import com.java.authentication.controller.LoginController;
import com.java.authentication.controller.LoginRequest;
import com.java.authentication.service.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenProvider tokenProvider;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Should return OK response when valid username and password are provided")
    public void shouldReturnOkResponseWhenValidUsernameAndPasswordAreProvided() {
        LoginRequest loginRequest = new LoginRequest("validUser", "validPassword");
        Authentication auth = new UsernamePasswordAuthenticationToken("validUser", "validPassword");
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(tokenProvider.createToken(auth)).thenReturn("validToken");

        ResponseEntity<?> response = loginController.authenticateUser(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(AuthResponse.class, response.getBody());
        assertEquals("validToken", ((AuthResponse) response.getBody()).getToken());
    }

    @Test
    @DisplayName("Should return bad request response when invalid username is provided")
    public void shouldReturnBadRequestResponseWhenInvalidUsernameIsProvided() {
        LoginRequest loginRequest = new LoginRequest("invalidUser", "validPassword");
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<?> response = loginController.authenticateUser(loginRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid username/password supplied", response.getBody());
    }

    @Test
    @DisplayName("Should return bad request response when invalid password is provided")
    public void shouldReturnBadRequestResponseWhenInvalidPasswordIsProvided() {
        LoginRequest loginRequest = new LoginRequest("validUser", "invalidPassword");
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<?> response = loginController.authenticateUser(loginRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid username/password supplied", response.getBody());
    }

    @Test
    @DisplayName("Should return bad request response when username and password are null")
    public void shouldReturnBadRequestResponseWhenUsernameAndPasswordAreNull() {
        LoginRequest loginRequest = new LoginRequest(null, null);
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<?> response = loginController.authenticateUser(loginRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid username/password supplied", response.getBody());
    }
}