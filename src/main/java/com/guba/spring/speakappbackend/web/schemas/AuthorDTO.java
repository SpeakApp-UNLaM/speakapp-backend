package com.guba.spring.speakappbackend.web.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String firstName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imageData;
}