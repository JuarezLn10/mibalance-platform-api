package com.jrzln.mibalanceapi.iam.domain.model.commands;

import com.jrzln.mibalanceapi.iam.domain.model.valueobjects.PasswordHash;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;

/**
 * @summary
 * Records the data required to sign up a new user.
 *
 * @param username the email of the user to be registered
 * @param password the password of the user to be registered
 */
public record SignUpCommand(Email username, PasswordHash password, String profileName, Integer profileAge, String profileRegion) {}