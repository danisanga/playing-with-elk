package com.danisanga.authentication.infrastructure.service.impl;

import com.danisanga.authentication.domain.models.UserModel;
import com.danisanga.authentication.domain.persistence.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final UserModel userModel = userRepository.findUserByEmail(email);
        final List<String> roles = getRoles();
        return User.builder()
                .username(userModel.getEmail())
                .password(userModel.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
    }

    private List<String> getRoles() {
        return List.of("USER");
    }
}
