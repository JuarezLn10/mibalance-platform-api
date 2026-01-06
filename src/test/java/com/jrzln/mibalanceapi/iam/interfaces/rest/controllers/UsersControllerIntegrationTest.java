package com.jrzln.mibalanceapi.iam.interfaces.rest.controllers;

import com.jrzln.mibalanceapi.iam.application.internal.queryservices.UserQueryServiceImpl;
import com.jrzln.mibalanceapi.iam.domain.model.queries.GetUserByIdQuery;
import com.jrzln.mibalanceapi.iam.domain.model.queries.GetUserByUserNameQuery;
import com.jrzln.mibalanceapi.iam.infrastructure.persistence.mongodb.repositories.UserRepository;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserQueryServiceImpl userQueryService;

    @BeforeEach
    void setUp() {
        userQueryService = mock(UserQueryServiceImpl.class);
        userRepository.deleteAll();
    }

    @Test
    void getUserByIdEndpoint_shouldReturnUser_whenIdExists() throws Exception {

        // Arrange
        var requestBody = """
            {
                "username": "nicolas@gmail.com",
                "password": "P@ssw0rd!"
            }
            """;

        MvcResult signUpResult = mockMvc.perform(post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        var responseBody = signUpResult.getResponse().getContentAsString();

        var generatedUserId = objectMapper
                .readTree(responseBody)
                .get("id")
                .asText();

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/{userId}", generatedUserId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(generatedUserId))
                .andExpect(jsonPath("$.username").value("nicolas@gmail.com"));
    }

    @Test
    void getUserByIdEndpoint_shouldReturn404_whenIdDoesNotExist() throws Exception {

        // Arrange
        var getUserByIdQuery = new GetUserByIdQuery("non-existing-id");

        // Act
        when(userQueryService.handle(getUserByIdQuery))
                .thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/{userId}", "non-existing-id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserByUserNameEndpoint_shouldReturnUser_whenEmailExists() throws Exception {

        // Arrange
        var signUpRequestBody = """
            {
                "username": "nicolas@gmail.com",
                "password": "P@ssw0rd!"
            }
            """;

        MvcResult signUpResult = mockMvc.perform(post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpRequestBody))
                .andExpect(status().isCreated())
                .andReturn();

        var responseBody = signUpResult.getResponse().getContentAsString();

        var generatedUserId = objectMapper
                .readTree(responseBody)
                .get("id")
                .asText();

        var requestBody = """
            {
                "username": "nicolas@gmail.com"
            }
            """;

        // Act & Assert
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(generatedUserId))
                .andExpect(jsonPath("$.username").value("nicolas@gmail.com"));
    }

    @Test
    void getUserByUserNameEndpoint_shouldReturn404_whenGetByEmailDoesNotExist() throws Exception {

        // Arrange
        var notExistingEmail = "notfound@gmail.com";

        var requestBody = """
                {
                    "username": "notfound@gmail.com"
                }
                """;

        var getUserByUsernameQuery = new GetUserByUserNameQuery(new Email(notExistingEmail));

        // Act
        when(userQueryService.handle(getUserByUsernameQuery))
                .thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }
}
