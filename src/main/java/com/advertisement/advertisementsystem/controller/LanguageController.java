package com.advertisement.advertisementsystem.controller;

import com.advertisement.advertisementsystem.model.dto.request.LanguageRequest;
import com.advertisement.advertisementsystem.model.dto.response.APIResponse;
import com.advertisement.advertisementsystem.model.dto.response.LanguageResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.advertisement.advertisementsystem.controller.LanguageController.LANGUAGE_API_PATH;

@Validated
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(value = LANGUAGE_API_PATH)
@Tag(name = "LanguageController", description = "Language API")
public class LanguageController {

    public static final String LANGUAGE_API_PATH = "/api/v0/languages";

    private final LanguageService languageService;

    @Operation(summary = "Save Language", tags = "LanguageController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saved Language"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<LanguageResponse>> save(
            @RequestBody @Valid LanguageRequest languageRequest
    ) {
        LanguageResponse language = languageService.save(languageRequest);

        return APIResponse.of(
                "Language with ID " + language.getId() + " were created",
                LANGUAGE_API_PATH,
                HttpStatus.CREATED,
                language
        );
    }

    @Operation(summary = "Find all Countries", tags = "LanguageController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all Countries"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<PageResponse<LanguageResponse>>> findAll(Pageable pageable) {
        PageResponse<LanguageResponse> languages = languageService.findAll(pageable);

        return APIResponse.of(
                "All Language: page_number: " + pageable.getPageNumber() +
                        "; page_size: " + pageable.getPageSize() +
                        "; page_sort: " + pageable.getSort(),
                LANGUAGE_API_PATH,
                HttpStatus.OK,
                languages
        );
    }

    @Operation(summary = "Find Language by ID", tags = "LanguageController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Language by ID"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<LanguageResponse>> findById(@PathVariable @NotNull @PositiveOrZero Long id) {
        LanguageResponse language = languageService.findById(id);

        return APIResponse.of(
                "Language with ID " + language.getId() + " were found",
                LANGUAGE_API_PATH + "/" + id,
                HttpStatus.OK,
                language
        );
    }

    @Operation(summary = "Update Language by ID", tags = "LanguageController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Language by ID"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<LanguageResponse>> update(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody @Valid LanguageRequest languageRequest
    ) {
        LanguageResponse language = languageService.update(id, languageRequest);

        return APIResponse.of(
                "Changes were applied to the Language with ID " + id,
                LANGUAGE_API_PATH + "/" + id,
                HttpStatus.OK,
                language
        );
    }

    @Operation(summary = "Restore Language by ID", tags = "LanguageController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restored Language by ID"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<APIResponse<LanguageResponse>> restoreById(
            @PathVariable @NotNull @PositiveOrZero Long id
    ) {
        LanguageResponse language = languageService.restoreById(id);

        return APIResponse.of(
                "Language with ID " + id + " were restored",
                LANGUAGE_API_PATH + "/restore/" + id,
                HttpStatus.OK,
                language
        );
    }

    @Operation(summary = "Delete Language by ID", tags = "LanguageController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Language by ID"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<APIResponse<LanguageResponse>> deleteById(
            @PathVariable @NotNull @PositiveOrZero Long id
    ) {
        LanguageResponse language = languageService.deleteById(id);

        return APIResponse.of(
                "Language with ID " + id + " were deleted",
                LANGUAGE_API_PATH + "/" + id,
                HttpStatus.OK,
                language
        );
    }
}
