package com.jrzln.mibalanceapi.profiles.application.internal.commandservices;

import com.jrzln.mibalanceapi.profiles.application.acl.ExternalAuthenticationService;
import com.jrzln.mibalanceapi.profiles.domain.model.aggregates.Profile;
import com.jrzln.mibalanceapi.profiles.domain.model.commands.CreateProfileCommand;
import com.jrzln.mibalanceapi.profiles.infrastructure.persistence.mongodb.repositories.ProfileRepository;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileCommandServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ExternalAuthenticationService authenticationService;

    @InjectMocks
    private ProfileCommandServiceImpl profileCommandService;

    @Test
    void createProfile_ShouldSaveProfileToRepository() {

        // Arrange
        var userId = "user-1";
        var name = "Nicolas";
        var age = 30;
        var region = "Lima";

        var profile = Profile.create(
                name,
                age,
                region,
                new UserId(userId)
        );

        var createProfileCommand = new CreateProfileCommand(
                name,
                age,
                region,
                new UserId(userId)
        );

        // Act
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        var createdProfile = profileCommandService.handle(createProfileCommand);

        // Assert
        assertTrue(createdProfile.isPresent());

        var created = createdProfile.get();

        assertEquals(new UserId(userId), created.getUserId());
        assertEquals(name, created.getName());
        assertEquals(age, created.getAge());
        assertEquals(region.toUpperCase(), created.getRegion().name());

    }
}
