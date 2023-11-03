package com.advertisement.advertisementsystem.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@Builder
@Schema(description = "Language Request")
public class LanguageRequest implements Serializable {

    @Length(max = 255, message = "Name is too long")
    @NotBlank(message = "Name cannot be empty")
    private String name;
}
