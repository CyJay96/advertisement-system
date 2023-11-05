package com.advertisement.advertisementsystem.controller;

import com.advertisement.advertisementsystem.model.dto.request.CountryRequest;
import com.advertisement.advertisementsystem.model.dto.response.APIResponse;
import com.advertisement.advertisementsystem.model.dto.response.CountryResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import static com.advertisement.advertisementsystem.controller.CountryController.COUNTRY_API_PATH;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = COUNTRY_API_PATH)
@Tag(name = "CountryController", description = "Country API")
public class CountryController {

    public static final String COUNTRY_API_PATH = "/api/v0/countries";

    private final CountryService countryService;

    @Operation(summary = "Save Country", tags = "CountryController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saved Country")
    })
    @PostMapping
    public ResponseEntity<APIResponse<CountryResponse>> save(
            @RequestBody @Valid CountryRequest countryRequest
    ) {
        CountryResponse country = countryService.save(countryRequest);

        return APIResponse.of(
                "Country with ID " + country.getId() + " were created",
                COUNTRY_API_PATH,
                HttpStatus.CREATED,
                country
        );
    }

    @Operation(summary = "Find all Countries", tags = "CountryController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all Countries")
    })
    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<CountryResponse>>> findAll(Pageable pageable) {
        PageResponse<CountryResponse> countries = countryService.findAll(pageable);

        return APIResponse.of(
                "All Country: page_number: " + pageable.getPageNumber() +
                        "; page_size: " + pageable.getPageSize() +
                        "; page_sort: " + pageable.getSort(),
                COUNTRY_API_PATH,
                HttpStatus.OK,
                countries
        );
    }

    @Operation(summary = "Find Country by ID", tags = "CountryController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Country by ID"),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CountryResponse>> findById(@PathVariable @NotNull @PositiveOrZero Long id) {
        CountryResponse country = countryService.findById(id);

        return APIResponse.of(
                "Country with ID " + country.getId() + " were found",
                COUNTRY_API_PATH + "/" + id,
                HttpStatus.OK,
                country
        );
    }

    @Operation(summary = "Update Country by ID", tags = "CountryController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Country by ID"),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CountryResponse>> update(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody @Valid CountryRequest countryRequest
    ) {
        CountryResponse country = countryService.update(id, countryRequest);

        return APIResponse.of(
                "Changes were applied to the Country with ID " + id,
                COUNTRY_API_PATH + "/" + id,
                HttpStatus.OK,
                country
        );
    }

    @Operation(summary = "Restore Country by ID", tags = "CountryController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restored Country by ID"),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PatchMapping("/restore/{id}")
    public ResponseEntity<APIResponse<CountryResponse>> restoreById(
            @PathVariable @NotNull @PositiveOrZero Long id
    ) {
        CountryResponse country = countryService.restoreById(id);

        return APIResponse.of(
                "Country with ID " + id + " were restored",
                COUNTRY_API_PATH + "/restore/" + id,
                HttpStatus.OK,
                country
        );
    }

    @Operation(summary = "Delete Country by ID", tags = "CountryController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Country by ID"),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<CountryResponse>> deleteById(
            @PathVariable @NotNull @PositiveOrZero Long id
    ) {
        CountryResponse country = countryService.deleteById(id);

        return APIResponse.of(
                "Country with ID " + id + " were deleted",
                COUNTRY_API_PATH + "/" + id,
                HttpStatus.OK,
                country
        );
    }
}
