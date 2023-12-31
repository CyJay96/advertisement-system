package com.advertisement.advertisementsystem.model.dto.request;

import com.advertisement.advertisementsystem.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

import static com.advertisement.advertisementsystem.util.Constants.EMAIL_REGEX;
import static com.advertisement.advertisementsystem.util.Constants.PHONE_REGEX;

@Data
@Builder
@Schema(description = "User Request")
public class UserRequest implements Serializable {

    @NotBlank(message = "Username cannot be empty")
    @Length(max = 255, message = "Username is too long")
    @JsonProperty(value = "username")
    private String username;

    @Length(max = 255, message = "User first name is too long")
    @JsonProperty(value = "firstName")
    private String firstName;

    @Length(max = 255, message = "User last name is too long")
    @JsonProperty(value = "lastName")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Length(max = 255, message = "User email is too long")
    @Pattern(regexp = EMAIL_REGEX, message = "Incorrect email address")
    @JsonProperty(value = "email")
    private String email;

    @Length(max = 255, message = "User phone is too long")
    @Pattern(regexp = PHONE_REGEX, message = "Incorrect phone number")
    @JsonProperty(value = "phone")
    private String phone;

    @JsonProperty(value = "roles")
    private List<RoleRequest> roles;

    @NotNull(message = "User status cannot be null")
    @JsonProperty(value = "status")
    private Status status;
}
