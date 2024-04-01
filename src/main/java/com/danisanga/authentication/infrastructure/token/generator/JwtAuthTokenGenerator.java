package com.danisanga.authentication.infrastructure.token.generator;

import io.jsonwebtoken.Claims;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

/**
 * JWT authentication token generator interface.
 */
public interface JwtAuthTokenGenerator extends AuthTokenGenerator {

    /**
     * Get token if it's present in the request.
     *
     * @param httpServletRequest    HTTP request
     * @return  Token if it's present, otherwise null.
     */
    String getToken(final HttpServletRequest httpServletRequest);

    /**
     * Get JWT Claims object.
     *
     * @param httpServletRequest    HTTP request
     * @return  JWT Claims object.
     */
    Claims getClaims(final HttpServletRequest httpServletRequest);

    /**
     * Check if Claims are valid.
     *
     * @param claims                    JWT Claims object
     * @return true if Claims are valid, false otherwise.
     * @throws AuthenticationException  {@link AuthenticationException}.
     */
    boolean areClaimsValid(Claims claims) throws AuthenticationException;
}
