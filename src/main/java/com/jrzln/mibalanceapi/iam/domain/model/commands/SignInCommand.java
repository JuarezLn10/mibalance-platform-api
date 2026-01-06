package com.jrzln.mibalanceapi.iam.domain.model.commands;

import com.jrzln.mibalanceapi.iam.domain.model.valueobjects.PasswordHash;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;

/**
 * @summary
 * Records the data required to sign in a user.
 *
 * @param username the email of the user
 * @param password the password of the user
 */
public record SignInCommand(Email username, PasswordHash password) {}
