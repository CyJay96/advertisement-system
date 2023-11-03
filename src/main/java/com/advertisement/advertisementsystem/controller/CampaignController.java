package com.advertisement.advertisementsystem.controller;

import com.advertisement.advertisementsystem.model.criteria.CampaignCriteria;
import com.advertisement.advertisementsystem.model.dto.request.CampaignRequest;
import com.advertisement.advertisementsystem.model.dto.response.APIResponse;
import com.advertisement.advertisementsystem.model.dto.response.CampaignResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.service.CampaignService;
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

import java.util.Objects;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = CampaignController.CAMPAIGN_API_PATH)
@Tag(name = "CampaignController", description = "Campaign API")
public class CampaignController {

    private final CampaignService campaignService;

    public static final String CAMPAIGN_API_PATH = "/api/v0/campaigns";

    @Operation(summary = "Save Campaign", tags = "CampaignController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saved Campaign")
    })
    @PostMapping("/{advertiserId}")
    public ResponseEntity<APIResponse<CampaignResponse>> saveByAdvertiserId(
            @PathVariable @NotNull @PositiveOrZero Long advertiserId,
            @RequestBody @Valid CampaignRequest campaignRequest) {
        CampaignResponse campaign = campaignService.save(advertiserId, campaignRequest);

        return APIResponse.of(
                "Campaign with ID " + campaign.getId() + " was created",
                CAMPAIGN_API_PATH,
                HttpStatus.CREATED,
                campaign
        );
    }

    @Operation(summary = "Find all Campaigns", tags = "CampaignController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all Campaigns")
    })
    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<CampaignResponse>>> findAll(Pageable pageable) {
        PageResponse<CampaignResponse> campaigns = campaignService.findAll(pageable);

        return APIResponse.of(
                "All Campaigns: page_number: " + pageable.getPageNumber() +
                        "; page_size: " + pageable.getPageSize(),
                CAMPAIGN_API_PATH,
                HttpStatus.OK,
                campaigns
        );
    }

    @Operation(summary = "Find all Campaigns by Criteria", tags = "CampaignController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all Campaigns by Criteria")
    })
    @GetMapping("/criteria")
    public ResponseEntity<APIResponse<PageResponse<CampaignResponse>>> findAllByCriteria(
            @RequestBody(required = false) CampaignCriteria searchCriteria,
            Pageable pageable) {
        searchCriteria = Objects.requireNonNullElse(searchCriteria, CampaignCriteria.builder().build());
        PageResponse<CampaignResponse> campaigns = campaignService.findAllByCriteria(searchCriteria, pageable);

        return APIResponse.of(
                "Campaigns by criteria: title: " + searchCriteria.getTitle() +
                        "; description: " + searchCriteria.getDescription() +
                        "; location: " + searchCriteria.getLocation() +
                        "; page_number: " + pageable.getPageNumber() +
                        "; page_size: " + pageable.getPageSize(),
                CAMPAIGN_API_PATH + "/criteria",
                HttpStatus.OK,
                campaigns
        );
    }

    @Operation(summary = "Find Campaign by ID", tags = "CampaignController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Campaign by ID"),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CampaignResponse>> findById(@PathVariable @NotNull @PositiveOrZero Long id) {
        CampaignResponse campaign = campaignService.findById(id);

        return APIResponse.of(
                "Campaign with ID " + campaign.getId() + " was found",
                CAMPAIGN_API_PATH + "/" + id,
                HttpStatus.OK,
                campaign
        );
    }

    @Operation(summary = "Update Campaign by ID", tags = "CampaignController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Campaign by ID"),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CampaignResponse>> update(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody @Valid CampaignRequest campaignRequest) {
        CampaignResponse campaign = campaignService.update(id, campaignRequest);

        return APIResponse.of(
                "Changes were applied to the Campaign with ID " + id,
                CAMPAIGN_API_PATH + "/" + id,
                HttpStatus.OK,
                campaign
        );
    }

    @Operation(summary = "Partial Update Campaign by ID", tags = "CampaignController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partial Updated Campaign by ID"),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @PatchMapping("/{id}")
    public ResponseEntity<APIResponse<CampaignResponse>> updatePartially(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody CampaignRequest campaignRequest) {
        CampaignResponse campaign = campaignService.update(id, campaignRequest);

        return APIResponse.of(
                "Partial changes were applied to the Campaign with ID " + id,
                CAMPAIGN_API_PATH + "/" + id,
                HttpStatus.OK,
                campaign
        );
    }

    @Operation(summary = "Delete Campaign by ID", tags = "CampaignController")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Campaign by ID"),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteById(
            @PathVariable @NotNull @PositiveOrZero Long id) {
        campaignService.deleteById(id);

        return APIResponse.of(
                "Campaign with ID " + id + " was deleted",
                CAMPAIGN_API_PATH + "/" + id,
                HttpStatus.OK,
                null
        );
    }
}
