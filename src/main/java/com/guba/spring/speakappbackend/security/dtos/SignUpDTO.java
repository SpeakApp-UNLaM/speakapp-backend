package com.guba.spring.speakappbackend.security.dtos;

import com.guba.spring.speakappbackend.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    @NotEmpty(message = "The username is required.")
    @Size(min = 6, max = 50, message = "The length of username must be between 6 and 50 characters.")
    private String username;

    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = {Flag.CASE_INSENSITIVE})
    private String email;

    @NotEmpty(message = "The password is required.")
    @Size(min = 6, max = 50, message = "The length of password must be between 6 and 50 characters.")
    private String password;

    @NotEmpty(message = "The firstName is required.")
    @Size(min = 6, max = 50, message = "The length of firstName must be between 6 and 50 characters.")
    private String firstName;

    @NotEmpty(message = "The lastName is required.")
    @Size(min = 6, max = 50, message = "The length of lastName must be between 6 and 50 characters.")
    private String lastName;

    private String code;

    @Valid
    @NotNull(message = "The type is required")
    private RoleEnum type;
}
