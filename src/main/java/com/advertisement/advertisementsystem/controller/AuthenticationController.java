package com.advertisement.advertisementsystem.controller;

import com.advertisement.advertisementsystem.model.dto.request.LoginRequest;
import com.advertisement.advertisementsystem.model.dto.request.RegisterRequest;
import com.advertisement.advertisementsystem.model.dto.response.APIResponse;
import com.advertisement.advertisementsystem.model.dto.response.AuthResponse;
import com.advertisement.advertisementsystem.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.advertisement.advertisementsystem.controller.AuthenticationController.AUTH_API_PATH;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = AUTH_API_PATH)
@Tag(name = "AuthenticationController", description = "Authentication API")
public class AuthenticationController {

    public static final String AUTH_API_PATH = "/api/v0/auth";

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register User", tags = "AuthenticationController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered User"),
            @ApiResponse(responseCode = "409", description = "User with such name or email already exists", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PostMapping("/register")
    public ResponseEntity<APIResponse<AuthResponse>> register(@RequestBody @Valid RegisterRequest registerRequest) {
        AuthResponse authResponse = authenticationService.register(registerRequest);

        return APIResponse.of(
                "User was registered",
                AUTH_API_PATH,
                HttpStatus.CREATED,
                authResponse
        );
    }

    @Operation(summary = "Authorize User", tags = "AuthenticationController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized User"),
            @ApiResponse(responseCode = "400", description = "Invalid username or password", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PostMapping("/login")
    public ResponseEntity<APIResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
        AuthResponse authResponse = authenticationService.login(loginRequest);

        return APIResponse.of(
                "User was authorized",
                AUTH_API_PATH,
                HttpStatus.OK,
                authResponse
        );
    }
}
