package com.advertisement.advertisementsystem.model.dto.response;

import com.advertisement.advertisementsystem.model.enums.Status;
import com.advertisement.advertisementsystem.model.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "Campaign Response")
public class CampaignResponse implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "location")
    private String location;

    @JsonProperty(value = "pictureUrl")
    private String pictureUrl;

    @JsonProperty(value = "countries")
    private List<CountryResponse> countries;

    @JsonProperty(value = "languages")
    private List<LanguageResponse> languages;

    @JsonProperty(value = "userType")
    private UserType userType;

    @JsonProperty(value = "status")
    private Status status;

    @JsonProperty(value = "createDate")
    private OffsetDateTime createDate;

    @JsonProperty(value = "lastUpdateDate")
    private OffsetDateTime lastUpdateDate;

    @JsonProperty(value = "advertiserId")
    private Long advertiserId;
}
