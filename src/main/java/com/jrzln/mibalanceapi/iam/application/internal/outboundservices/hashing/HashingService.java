package com.jrzln.mibalanceapi.iam.application.internal.outboundservices.hashing;

/**
 * Service for hashing operations such as encoding and matching passwords
 */
public interface HashingService {

    /**
     * Encodes the raw password
     *
     * @param rawPassword the raw password to encode
     * @return the encoded password
     */
    String encode(CharSequence rawPassword);

    /**
     * Checks if the raw password matches the encoded password
     *
     * @param rawPassword     the raw password to check
     * @param encodedPassword the encoded password to compare against
     * @return true if the passwords match, false otherwise
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
