package com.guba.spring.speakappbackend.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotEmpty(message = "The username is required.")
    @Size(min = 6, max = 50, message = "The length of username must be between 6 and 50 characters.")
    private String username;

    @NotEmpty(message = "The password is required.")
    @Size(min = 6, max = 50, message = "The length of password must be between 6 and 50 characters.")
    private String password;

}
