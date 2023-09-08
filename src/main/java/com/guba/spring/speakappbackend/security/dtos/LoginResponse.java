package com.guba.spring.speakappbackend.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long idUser;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String imageData;
    private String token;
}
