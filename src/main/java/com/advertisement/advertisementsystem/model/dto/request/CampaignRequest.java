package com.advertisement.advertisementsystem.model.dto.request;

import com.advertisement.advertisementsystem.model.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Schema(description = "Campaign Request")
public class CampaignRequest implements Serializable {

    @NotBlank(message = "Title cannot be empty")
    @Length(max = 255, message = "Title is too long")
    @JsonProperty(value = "title")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    @Length(max = 255, message = "Description is too long")
    @JsonProperty(value = "description")
    private String description;

    @NotBlank(message = "Location cannot be empty")
    @Length(max = 255, message = "Location is too long")
    @JsonProperty(value = "location")
    private String location;

    @NotBlank(message = "Picture url cannot be empty")
    @Length(max = 255, message = "Picture url is too long")
    @JsonProperty(value = "pictureUrl")
    private String pictureUrl;

    @JsonProperty(value = "countries")
    private List<CountryRequest> countries;

    @JsonProperty(value = "languages")
    private List<LanguageRequest> languages;

    @NotNull(message = "User type cannot be null")
    @JsonProperty(value = "userType")
    private UserType userType;
}
