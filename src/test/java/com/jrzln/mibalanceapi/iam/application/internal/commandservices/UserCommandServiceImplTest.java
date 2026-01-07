package com.jrzln.mibalanceapi.iam.application.internal.commandservices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jrzln.mibalanceapi.iam.application.acl.ExternalProfileManagementService;
import com.jrzln.mibalanceapi.iam.application.internal.outboundservices.hashing.HashingService;
import com.jrzln.mibalanceapi.iam.application.internal.outboundservices.tokens.TokenService;
import com.jrzln.mibalanceapi.iam.domain.model.aggregates.User;
import com.jrzln.mibalanceapi.iam.domain.model.commands.SignInCommand;
import com.jrzln.mibalanceapi.iam.domain.model.commands.SignUpCommand;
import com.jrzln.mibalanceapi.iam.domain.model.exceptions.UserNotFoundException;
import com.jrzln.mibalanceapi.iam.domain.model.exceptions.UserSaveFailedException;
import com.jrzln.mibalanceapi.iam.domain.model.exceptions.UsernameAlreadyExistsException;
import com.jrzln.mibalanceapi.iam.domain.model.valueobjects.PasswordHash;
import com.jrzln.mibalanceapi.iam.infrastructure.persistence.mongodb.repositories.UserRepository;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UserCommandServiceImplTest {

    private UserRepository userRepository;
    private HashingService hashingService;
    private TokenService tokenService;

    private UserCommandServiceImpl userCommandService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        hashingService = mock(HashingService.class);
        tokenService = mock(TokenService.class);
        var profileManagementService = mock(ExternalProfileManagementService.class);

        userCommandService = new UserCommandServiceImpl(userRepository, hashingService, tokenService, profileManagementService);
    }

    @Test
    void handleSignUp_shouldCreateUserSuccessfully() {

        // Arrange
        var email = new Email("test@email.com");
        var password = new PasswordHash("rawPassword");

        var profileName = "Test User";
        var profileAge = 30;
        var profileRegion = "Lima";

        var command = new SignUpCommand(email, password, profileName, profileAge, profileRegion);

        // Act
        when(userRepository.existsByUsername(email)).thenReturn(false);
        when(hashingService.encode(password.value())).thenReturn("hashedPassword");

        var result = userCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getUsername());
        assertEquals("hashedPassword", result.get().getPasswordHash().value());
        verify(userRepository).save(result.get());
    }

    @Test
    void handleSignUp_shouldThrowException_whenUsernameExists() {

        // Arrange
        var email = new Email("existing@email.com");
        var password = new PasswordHash("pass");

        var profileName = "Test User";
        var profileAge = 30;
        var profileRegion = "Lima";

        SignUpCommand command = new SignUpCommand(email, password, profileName, profileAge, profileRegion);

        // Act
        when(userRepository.existsByUsername(email)).thenReturn(true);

        // Assert
        assertThrows(UsernameAlreadyExistsException.class,
                () -> userCommandService.handle(command)
        );

        verify(userRepository, never()).save(any());
    }

    @Test
    void handleSignUp_shouldThrowException_whenRepositoryFails() {

        // Arrange
        var email = new Email("fail@email.com");
        var password = new PasswordHash("pass");

        var profileName = "Test User";
        var profileAge = 30;
        var profileRegion = "Lima";

        SignUpCommand command = new SignUpCommand(email, password, profileName, profileAge, profileRegion);

        // Act
        when(userRepository.existsByUsername(email)).thenReturn(false);
        when(hashingService.encode(password.value())).thenReturn("hashedPassword");

        // Simulate repository failure
        doThrow(new RuntimeException("DB error"))
                .when(userRepository).save(any());

        // Assert
        assertThrows(UserSaveFailedException.class,
                () -> userCommandService.handle(command)
        );
    }

    @Test
    void handleSignIn_shouldReturnUserTokenAndId() {

        // Arrange
        var email = new Email("test@email.com");
        var password = new PasswordHash("rawPassword");
        var user = User.create(email, new PasswordHash("hashedPassword"));
        user.setId("user-123");

        var command = new SignInCommand(email, password);

        // Act
        when(userRepository.findByUsername(email)).thenReturn(Optional.of(user));
        when(hashingService.matches(password.value(), "hashedPassword")).thenReturn(true);
        when(tokenService.generateToken(email.email())).thenReturn("token-xyz");

        var result = userCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        ImmutableTriple<User, String, String> triple = result.get();

        assertEquals(user, triple.getLeft());
        assertEquals("token-xyz", triple.getMiddle());
        assertEquals("user-123", triple.getRight());
    }

    @Test
    void handleSignIn_shouldThrowException_whenUserNotFound() {

        // Arrange
        var email = new Email("nouser@email.com");
        var password = new PasswordHash("pass");

        SignInCommand command = new SignInCommand(email, password);

        // Act
        when(userRepository.findByUsername(email)).thenReturn(Optional.empty());

        // Assert
        assertThrows(UserNotFoundException.class,
                () -> userCommandService.handle(command)
        );
    }

    @Test
    void handleSignIn_shouldThrowException_whenPasswordInvalid() {

        // Arrange
        var email = new Email("test@email.com");
        var password = new PasswordHash("wrongPass");
        User user = User.create(email, new PasswordHash("hashedPassword"));

        SignInCommand command = new SignInCommand(email, password);

        // Act
        when(userRepository.findByUsername(email)).thenReturn(Optional.of(user));
        when(hashingService.matches(password.value(), "hashedPassword")).thenReturn(false);

        // Assert
        assertThrows(Exception.class,
                () -> userCommandService.handle(command)
        );
    }
}
