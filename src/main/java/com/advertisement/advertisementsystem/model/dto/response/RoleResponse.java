package com.advertisement.advertisementsystem.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Schema(description = "Role Response")
public class RoleResponse implements Serializable {

    private Long id;

    private String name;
}
