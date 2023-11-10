package com.advertisement.advertisementsystem.model.dto.response;

import com.advertisement.advertisementsystem.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Builder
@Schema(description = "Advertiser Response")
public class AdvertiserResponse implements Serializable {

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

    @JsonProperty(value = "status")
    private Status status;

    @JsonProperty(value = "createDate")
    private OffsetDateTime createDate;

    @JsonProperty(value = "lastUpdateDate")
    private OffsetDateTime lastUpdateDate;

    @JsonProperty(value = "campaigns")
    private PageResponse<CampaignResponse> campaigns;
}
