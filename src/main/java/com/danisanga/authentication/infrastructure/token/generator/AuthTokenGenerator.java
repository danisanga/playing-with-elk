package com.danisanga.authentication.infrastructure.token.generator;

import com.danisanga.authentication.domain.models.UserModel;

/**
 * Generic authentication token generator interface.
 */
public interface AuthTokenGenerator {

    String generateToken(final String email);
}
