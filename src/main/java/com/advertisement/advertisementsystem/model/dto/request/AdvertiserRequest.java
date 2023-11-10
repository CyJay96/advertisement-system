package com.advertisement.advertisementsystem.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Advertiser Request")
public class AdvertiserRequest implements Serializable {

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
}
