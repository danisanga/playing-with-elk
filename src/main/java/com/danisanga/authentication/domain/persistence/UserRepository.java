package com.danisanga.authentication.domain.persistence;

import com.danisanga.authentication.domain.models.UserModel;

public interface UserRepository {

    /**
     * Find user by email.
     *
     * @param email email address
     * @return  User object for requesting email.
     */
    UserModel findUserByEmail(String email);
}
