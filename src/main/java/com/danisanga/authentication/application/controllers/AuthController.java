package com.danisanga.authentication.application.controllers;

import com.danisanga.authentication.application.requests.LoginRequest;
import com.danisanga.authentication.application.responses.LoginResponse;
import com.danisanga.authentication.infrastructure.token.generator.JwtAuthTokenGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthTokenGenerator jwtAuthTokenGenerator;

    /**
     * Default constructor.
     *
     * @param authenticationManager injected
     * @param jwtAuthTokenGenerator injected
     */
    public AuthController(final AuthenticationManager authenticationManager,
                          final JwtAuthTokenGenerator jwtAuthTokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtAuthTokenGenerator = jwtAuthTokenGenerator;

    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequestWsDTO)  {

        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestWsDTO.getEmail(),
                            loginRequestWsDTO.getPassword()));

            final String email = authentication.getName();
            final String token = jwtAuthTokenGenerator.generateToken(email);
            final LoginResponse loginRes = new LoginResponse(email,token);
            return ResponseEntity.ok(loginRes);

        }catch (final BadCredentialsException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
