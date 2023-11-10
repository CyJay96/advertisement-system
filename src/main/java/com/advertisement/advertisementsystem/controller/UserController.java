package com.advertisement.advertisementsystem.controller;

import com.advertisement.advertisementsystem.model.dto.request.UserRequest;
import com.advertisement.advertisementsystem.model.dto.response.APIResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.dto.response.UserResponse;
import com.advertisement.advertisementsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.advertisement.advertisementsystem.controller.UserController.USER_API_PATH;

@RestController
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(value = USER_API_PATH)
@Tag(name = "UserController", description = "User API")
public class UserController {

    public static final String USER_API_PATH = "/api/v0/users";

    private final UserService userService;

    @Operation(summary = "Find all Users", tags = "UserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all Users"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<APIResponse<PageResponse<UserResponse>>> findAll(Pageable pageable) {
        PageResponse<UserResponse> users = userService.findAll(pageable);

        return APIResponse.of(
                "All Users: page_number: " + pageable.getPageNumber() +
                        "; page_size: " + pageable.getPageSize() +
                        "; page_sort: " + pageable.getSort(),
                USER_API_PATH,
                HttpStatus.OK,
                users
        );
    }

    @Operation(summary = "Find User by ID", tags = "UserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found User by ID"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<UserResponse>> findById(
            @PathVariable @NotNull @PositiveOrZero Long id,
            Principal principal
    ) {
        UserResponse user = userService.findById(id, principal);

        return APIResponse.of(
                "User with ID " + user.getId() + " was found",
                USER_API_PATH + "/" + id,
                HttpStatus.OK,
                user
        );
    }

    @Operation(summary = "Find User by Username", tags = "UserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found User by Username"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @GetMapping("/byUsername/{username}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<UserResponse>> findByUsername(
            @PathVariable @NotBlank String username,
            Principal principal
    ) {
        UserResponse user = userService.findByUsername(username, principal);

        return APIResponse.of(
                "User with username " + user.getUsername() + " was found",
                USER_API_PATH + "/byUsername/" + username,
                HttpStatus.OK,
                user
        );
    }

    @Operation(summary = "Update User by ID", tags = "UserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated User by ID"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "409", description = "User with such name or email already exists", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<UserResponse>> update(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody @Valid UserRequest userRequest,
            Principal principal
    ) {
        UserResponse user = userService.update(id, userRequest, principal);

        return APIResponse.of(
                "Changes were applied to the User with ID " + id,
                USER_API_PATH + "/" + id,
                HttpStatus.OK,
                user
        );
    }

    @Operation(summary = "Partial Update User by ID", tags = "UserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partial Updated User by ID"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "409", description = "User with such name or email already exists", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<UserResponse>> updatePartially(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody UserRequest userRequest,
            Principal principal
    ) {
        UserResponse user = userService.update(id, userRequest, principal);

        return APIResponse.of(
                "Partial changes were applied to the User with ID " + id,
                USER_API_PATH + "/" + id,
                HttpStatus.OK,
                user
        );
    }

    @Operation(summary = "Restore User by ID", tags = "UserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restored User by ID"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<APIResponse<UserResponse>> restoreById(@PathVariable @NotNull @PositiveOrZero Long id) {
        UserResponse user = userService.restoreById(id);

        return APIResponse.of(
                "User with ID " + id + " was restored",
                USER_API_PATH + "/restore/" + id,
                HttpStatus.OK,
                user
        );
    }

    @Operation(summary = "Delete User by ID", tags = "UserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted User by ID"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<APIResponse<UserResponse>> deleteById(@PathVariable @NotNull @PositiveOrZero Long id) {
        UserResponse user = userService.deleteById(id);

        return APIResponse.of(
                "User with ID " + id + " was deleted",
                USER_API_PATH + "/" + id,
                HttpStatus.OK,
                user
        );
    }
}
