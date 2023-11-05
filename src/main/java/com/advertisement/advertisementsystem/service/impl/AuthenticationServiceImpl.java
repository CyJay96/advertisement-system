package com.advertisement.advertisementsystem.service.impl;

import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.exception.UserExistenceException;
import com.advertisement.advertisementsystem.model.dto.request.LoginRequest;
import com.advertisement.advertisementsystem.model.dto.request.RegisterRequest;
import com.advertisement.advertisementsystem.model.dto.response.AuthResponse;
import com.advertisement.advertisementsystem.model.entity.User;
import com.advertisement.advertisementsystem.model.enums.Role;
import com.advertisement.advertisementsystem.model.enums.Status;
import com.advertisement.advertisementsystem.repository.RoleRepository;
import com.advertisement.advertisementsystem.repository.UserRepository;
import com.advertisement.advertisementsystem.security.jwt.JwtTokenProvider;
import com.advertisement.advertisementsystem.service.AuthenticationService;
import com.advertisement.advertisementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(final RegisterRequest registerRequest) {
        try {
            final User user = User.builder()
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .email(registerRequest.getEmail())
                    .roles(List.of(
                            roleRepository.findByName(Role.ROLE_USER.name())
                                    .orElseThrow(() -> new EntityNotFoundException(Role.class))
                    ))
                    .createDate(OffsetDateTime.now())
                    .lastUpdateDate(OffsetDateTime.now())
                    .status(Status.ACTIVE)
                    .build();
            userRepository.save(user);

            return login(LoginRequest.builder()
                    .username(registerRequest.getUsername())
                    .password(registerRequest.getPassword())
                    .build());
        } catch (DataIntegrityViolationException e) {
            throw new UserExistenceException(registerRequest.getUsername(), registerRequest.getEmail());
        }
    }

    @Override
    public AuthResponse login(final LoginRequest loginRequest) {
        try {
            final String username = loginRequest.getUsername();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword()));

            final User user = userService.findEntityByUsername(username);

            final String token = jwtTokenProvider.createToken(user);

            return AuthResponse.builder()
                    .token(token)
                    .build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
