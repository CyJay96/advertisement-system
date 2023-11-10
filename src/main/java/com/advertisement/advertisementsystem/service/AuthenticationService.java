package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.model.dto.request.LoginRequest;
import com.advertisement.advertisementsystem.model.dto.request.RegisterRequest;
import com.advertisement.advertisementsystem.model.dto.response.AuthResponse;

public interface AuthenticationService {

    AuthResponse register(final RegisterRequest registerRequest);

    AuthResponse login(final LoginRequest loginRequest);
}
