package com.advertisement.advertisementsystem.model.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@Schema(description = "Campaign search criteria DTO")
public class CampaignCriteria {

    @Length(max = 255, message = "Title is too long")
    private String title;

    @Length(max = 255, message = "Description is too long")
    private String description;

    @Length(max = 255, message = "Location is too long")
    private String location;

    @PositiveOrZero(message = "Page must be zero or more")
    private Integer page;

    @PositiveOrZero(message = "Page size must be zero or more")
    private Integer size;
}
