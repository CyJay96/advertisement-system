package com.advertisement.advertisementsystem.model.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Campaign search criteria DTO")
public class CampaignCriteria {

    private String title;

    private String description;

    private String location;

    private Integer page;

    private Integer size;
}
