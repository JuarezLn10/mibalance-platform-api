package com.jrzln.mibalanceapi.iam.infrastructure.hashing.bcrypt;

import com.jrzln.mibalanceapi.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Service for hashing passwords using BCrypt algorithm
 */
public interface BCryptHashingService extends HashingService, PasswordEncoder {}