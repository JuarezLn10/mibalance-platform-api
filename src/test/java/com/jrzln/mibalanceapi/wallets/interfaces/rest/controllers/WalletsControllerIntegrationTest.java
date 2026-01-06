package com.jrzln.mibalanceapi.wallets.interfaces.rest.controllers;

import com.jrzln.mibalanceapi.iam.infrastructure.persistence.mongodb.repositories.UserRepository;
import com.jrzln.mibalanceapi.wallets.infrastructure.persistence.mongodb.repositories.WalletRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WalletsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String generatedUserId;

    @BeforeEach
    void setUp() throws Exception {
        walletRepository.deleteAll();
        userRepository.deleteAll();

        // Create a mock user
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

        generatedUserId = objectMapper
                .readTree(responseBody)
                .get("id")
                .asText();
    }

    @Test
    void getWalletByIdEndpoint_shouldReturnWallet() throws Exception {

        // Arrange
        var registeringRequestBody = """
                {
                    "name": "YAPE",
                    "type": "SAVINGS",
                    "initialBalance": 150.0,
                    "currency": "PEN"
                }
                """;

        assertThat(generatedUserId).isNotNull();
        assertThat(generatedUserId).isNotBlank();

        MvcResult registerResponse = mockMvc.perform(post("/api/v1/users/{userId}/wallets", generatedUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registeringRequestBody))
                .andExpect(status().isCreated())
                .andReturn();

        var responseBody = registerResponse.getResponse().getContentAsString();

        var generatedWalletId = objectMapper
                .readTree(responseBody)
                .get("walletId")
                .asText();

        // Act & Assert
        mockMvc.perform(get("/api/v1/wallets/{walletId}", generatedWalletId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(generatedWalletId));
    }

    @Test
    void getWalletByIdEndpoint_shouldReturn404_whenWalletIdIsInvalid() throws Exception {

        // Arrange
        var invalidWalletId = "invalid-wallet-id";

        var registeringRequestBody = """
                {
                    "name": "YAPE",
                    "type": "SAVINGS",
                    "initialBalance": 150.0,
                    "currency": "PEN"
                }
                """;

        assertThat(generatedUserId).isNotNull();
        assertThat(generatedUserId).isNotBlank();

        mockMvc.perform(post("/api/v1/users/{userId}/wallets", generatedUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registeringRequestBody))
                .andExpect(status().isCreated());

        // Act & Assert
        mockMvc.perform(get("/api/v1/wallets/{walletId}", invalidWalletId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
