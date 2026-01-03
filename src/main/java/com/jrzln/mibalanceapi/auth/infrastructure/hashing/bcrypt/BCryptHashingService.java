package com.jrzln.mibalanceapi.auth.infrastructure.hashing.bcrypt;

import com.jrzln.mibalanceapi.auth.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Service for hashing passwords using BCrypt algorithm
 */
public interface BCryptHashingService extends HashingService, PasswordEncoder {}