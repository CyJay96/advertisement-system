package com.advertisement.advertisementsystem.model.dto.response;

import com.advertisement.advertisementsystem.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Schema(description = "Language Response")
public class LanguageResponse implements Serializable {

    private Long id;

    private String name;

    private Status status;
}
