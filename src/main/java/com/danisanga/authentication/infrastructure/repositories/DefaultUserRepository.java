package com.danisanga.authentication.infrastructure.repositories;

import com.danisanga.authentication.domain.models.UserModel;
import com.danisanga.authentication.domain.persistence.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultUserRepository implements UserRepository {

    /**
     * {@inheritDoc}
     */
    public UserModel findUserByEmail(String email) {
        return new UserModel(email,"123456");
    }
}
