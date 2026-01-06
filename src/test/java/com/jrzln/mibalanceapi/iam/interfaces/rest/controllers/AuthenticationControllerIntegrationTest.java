package com.jrzln.mibalanceapi.iam.interfaces.rest.controllers;

import com.jrzln.mibalanceapi.iam.domain.model.aggregates.User;
import com.jrzln.mibalanceapi.iam.domain.model.valueobjects.PasswordHash;
import com.jrzln.mibalanceapi.iam.infrastructure.hashing.bcrypt.services.HashingServiceImpl;
import com.jrzln.mibalanceapi.iam.infrastructure.persistence.mongodb.repositories.UserRepository;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("hashingServiceImpl")
    private HashingServiceImpl hashingService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void signUpEndpoint_shouldCreateUser() throws Exception {

        // Arrange
        var requestBody = """
            {
                "username": "test@email.com",
                "password": "P@ssw0rd!"
            }
        """;

        // Act
        mockMvc.perform(post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("test@email.com"))
                .andExpect(jsonPath("$.id").isNotEmpty());

        // Assert
        assertTrue(userRepository.existsByUsername(new Email("test@email.com")));
    }

    @Test
    void signUp_shouldReturnBadRequest_whenUsernameAlreadyExists() throws Exception {

        // Arrange
        var existingUser = User.create(
                new Email("duplicate@email.com"),
                new PasswordHash("hashed-password")
        );
        userRepository.save(existingUser);

        var requestBody = """
            {
                "username": "duplicate@email.com",
                "password": "P@ssw0rd!"
            }
        """;

        mockMvc.perform(post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signInEndpoint_shouldReturnAuthenticatedUser() throws Exception {

        // Arrange
        var email = new Email("login@test.com");
        var hash = new PasswordHash(hashingService.encode("secret123"));
        var user = User.create(email, hash);
        userRepository.save(user);

        var requestBody = """
            {
                "username": "login@test.com",
                "password": "secret123"
            }
        """;

        mockMvc.perform(post("/api/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("login@test.com"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.userId").value(user.getId()));
    }

    @Test
    void signInEndpoint_shouldReturnNotFoundForNonExistentUser() throws Exception {

        // Arrange
        var requestBody = """
            {
                "username": "unknown@test.com",
                "password": "whatever"
            }
        """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void signInEndpoint_shouldReturnNotFoundForWrongPassword() throws Exception {

        // Arrange
        var email = new Email("user@test.com");
        var hash = new PasswordHash(hashingService.encode("correctPassword"));
        var user = User.create(email, hash);
        userRepository.save(user);

        var requestBody = """
            {
                "username": "user@test.com",
                "password": "wrongPassword"
            }
        """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }
}