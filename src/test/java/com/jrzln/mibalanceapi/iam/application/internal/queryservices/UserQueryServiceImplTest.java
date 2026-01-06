package com.jrzln.mibalanceapi.iam.application.internal.queryservices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.jrzln.mibalanceapi.iam.domain.model.aggregates.User;
import com.jrzln.mibalanceapi.iam.domain.model.queries.GetUserByIdQuery;
import com.jrzln.mibalanceapi.iam.domain.model.queries.GetUserByUserNameQuery;
import com.jrzln.mibalanceapi.iam.domain.model.valueobjects.PasswordHash;
import com.jrzln.mibalanceapi.iam.infrastructure.persistence.mongodb.repositories.UserRepository;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserQueryServiceImplTest {

    private UserRepository userRepository;
    private UserQueryServiceImpl userQueryService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userQueryService = new UserQueryServiceImpl(userRepository);
    }

    @Test
    void handleGetUserByIdQuery_shouldReturnUserIfExists() {

        // Arrange
        var user = User.create(new Email("test@email.com"), new PasswordHash("hash"));
        user.setId("user-123");

        var query = new GetUserByIdQuery("user-123");

        // Act
        when(userRepository.findById("user-123"))
                .thenReturn(Optional.of(user));

        var result = userQueryService.handle(query);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void handleGetUserByIdQuery_shouldReturnEmptyIfUserNotFound() {

        // Arrange
        var query = new GetUserByIdQuery("non-existent");

        // Act
        when(userRepository.findById("non-existent"))
                .thenReturn(Optional.empty());

        var result = userQueryService.handle(query);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void handleGetUserByUserNameQuery_shouldReturnUserIfExists() {

        // Arrange
        var email = new Email("user@email.com");
        var user = User.create(email, new PasswordHash("hash"));

        var query = new GetUserByUserNameQuery(email);

        // Act
        when(userRepository.findByUsername(email))
                .thenReturn(Optional.of(user));

        var result = userQueryService.handle(query);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void handleGetUserByUserNameQuery_shouldReturnEmptyIfUserNotFound() {

        // Arrange
        var email = new Email("missing@email.com");
        var query = new GetUserByUserNameQuery(email);

        // Act
        when(userRepository.findByUsername(email))
                .thenReturn(Optional.empty());

        var result = userQueryService.handle(query);

        // Assert
        assertTrue(result.isEmpty());
    }
}