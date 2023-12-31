package com.advertisement.advertisementsystem.controller;

import com.advertisement.advertisementsystem.model.criteria.AdvertiserCriteria;
import com.advertisement.advertisementsystem.model.dto.request.AdvertiserRequest;
import com.advertisement.advertisementsystem.model.dto.response.APIResponse;
import com.advertisement.advertisementsystem.model.dto.response.AdvertiserResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.service.AdvertiserService;
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

import java.util.Objects;

import static com.advertisement.advertisementsystem.controller.AdvertiserController.ADVERTISER_API_PATH;

@Validated
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(value = ADVERTISER_API_PATH)
@Tag(name = "AdvertiserController", description = "Advertiser API")
public class AdvertiserController {

    public static final String ADVERTISER_API_PATH = "/api/v0/advertisers";

    private final AdvertiserService advertiserService;

    @Operation(summary = "Save Advertiser", tags = "AdvertiserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saved Advertiser"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<AdvertiserResponse>> save(
            @RequestBody @Valid AdvertiserRequest advertiserRequest
    ) {
        AdvertiserResponse advertiser = advertiserService.save(advertiserRequest);

        return APIResponse.of(
                "Advertiser with ID " + advertiser.getId() + " were created",
                ADVERTISER_API_PATH,
                HttpStatus.CREATED,
                advertiser
        );
    }

    @Operation(summary = "Find all Advertiser", tags = "AdvertiserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all Advertiser"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<PageResponse<AdvertiserResponse>>> findAll(Pageable pageable) {
        PageResponse<AdvertiserResponse> advertiser = advertiserService.findAll(pageable);

        return APIResponse.of(
                "All Advertiser: page_number: " + pageable.getPageNumber() +
                        "; page_size: " + pageable.getPageSize() +
                        "; page_sort: " + pageable.getSort(),
                ADVERTISER_API_PATH,
                HttpStatus.OK,
                advertiser
        );
    }

    @Operation(summary = "Find all Advertiser by Criteria", tags = "AdvertiserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all Advertiser by Criteria"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @GetMapping("/criteria")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<PageResponse<AdvertiserResponse>>> findAllByCriteria(
            @RequestBody(required = false) @Valid AdvertiserCriteria searchCriteria,
            Pageable pageable
    ) {
        searchCriteria = Objects.requireNonNullElse(searchCriteria, AdvertiserCriteria.builder().build());
        PageResponse<AdvertiserResponse> advertiser = advertiserService.findAllByCriteria(searchCriteria, pageable);

        return APIResponse.of(
                "Advertiser by criteria: title: " + searchCriteria.getTitle() +
                        "; description: " + searchCriteria.getDescription() +
                        "; location: " + searchCriteria.getLocation() +
                        "; page_number: " + pageable.getPageNumber() +
                        "; page_size: " + pageable.getPageSize() +
                        "; page_sort: " + pageable.getSort(),
                ADVERTISER_API_PATH + "/criteria",
                HttpStatus.OK,
                advertiser
        );
    }

    @Operation(summary = "Find Advertiser by ID", tags = "AdvertiserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Advertiser by ID"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<AdvertiserResponse>> findById(
            @PathVariable @NotNull @PositiveOrZero Long id,
            Pageable pageable
    ) {
        AdvertiserResponse advertiser = advertiserService.findById(id, pageable);

        return APIResponse.of(
                "Advertiser with ID " + advertiser.getId() +
                        " were found: page_number: " + pageable.getPageNumber() +
                        "; page_size: " + pageable.getPageSize() +
                        "; page_sort: " + pageable.getSort(),
                ADVERTISER_API_PATH + "/" + id,
                HttpStatus.OK,
                advertiser
        );
    }

    @Operation(summary = "Update Advertiser by ID", tags = "AdvertiserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Advertiser by ID"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<AdvertiserResponse>> update(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody @Valid AdvertiserRequest advertiserRequest
    ) {
        AdvertiserResponse advertiser = advertiserService.update(id, advertiserRequest);

        return APIResponse.of(
                "Changes were applied to the Advertiser with ID " + id,
                ADVERTISER_API_PATH + "/" + id,
                HttpStatus.OK,
                advertiser
        );
    }

    @Operation(summary = "Partial Update Advertiser by ID", tags = "AdvertiserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partial Updated Advertiser by ID"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Access is forbidden for this role", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse<AdvertiserResponse>> updatePartially(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody AdvertiserRequest advertiserRequest
    ) {
        AdvertiserResponse advertiser = advertiserService.update(id, advertiserRequest);

        return APIResponse.of(
                "Partial changes were applied to the Advertiser with ID " + id,
                ADVERTISER_API_PATH + "/" + id,
                HttpStatus.OK,
                advertiser
        );
    }

    @Operation(summary = "Restore Advertiser by ID", tags = "AdvertiserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restored Advertiser by ID"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<APIResponse<AdvertiserResponse>> restoreById(
            @PathVariable @NotNull @PositiveOrZero Long id
    ) {
        AdvertiserResponse advertiser = advertiserService.restoreById(id);

        return APIResponse.of(
                "Advertiser with ID " + id + " were restored",
                ADVERTISER_API_PATH + "/restore/" + id,
                HttpStatus.OK,
                advertiser
        );
    }

    @Operation(summary = "Delete Advertiser by ID", tags = "AdvertiserController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Advertiser by ID"),
            @ApiResponse(responseCode = "401", description = "Access is forbidden to unauthorized users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<APIResponse<AdvertiserResponse>> deleteById(
            @PathVariable @NotNull @PositiveOrZero Long id
    ) {
        AdvertiserResponse advertiser = advertiserService.deleteById(id);

        return APIResponse.of(
                "Advertiser with ID " + id + " were deleted",
                ADVERTISER_API_PATH + "/" + id,
                HttpStatus.OK,
                advertiser
        );
    }
}
