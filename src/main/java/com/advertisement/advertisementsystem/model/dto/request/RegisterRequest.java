package com.advertisement.advertisementsystem.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static com.advertisement.advertisementsystem.util.Constants.EMAIL_REGEX;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Register Request")
public class RegisterRequest implements Serializable {

    @NotBlank(message = "Username cannot be empty")
    @Length(max = 255, message = "Username is too long")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Length(max = 255, message = "User email is too long")
    @Pattern(regexp = EMAIL_REGEX, message = "Incorrect email address")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Length(max = 255, message = "Password is too long")
    private String password;
}
