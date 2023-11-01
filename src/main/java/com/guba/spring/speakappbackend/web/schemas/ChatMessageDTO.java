package com.guba.spring.speakappbackend.web.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ChatMessageDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private Long fromUser;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long toUser;
    @NotEmpty(message = "The password is required.")
    @NotNull
    private String status;
    @NotEmpty(message = "The password is required.")
    @NotNull
    private String text;//MESSAGE
    @NotEmpty(message = "The password is required.")
    @NotNull
    private String type;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long createdAt;//MILLIS

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AuthorDTO author;

    public ChatMessageDTO(
            String status, String text, String type,
            Long createdAt, Long id, AuthorDTO author) {
        this.status = status;
        this.text = text;
        this.type = type;
        this.createdAt = createdAt;
        this.id = id;
        this.author = author;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorDTO {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private Long id;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String firstName;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String lastName;
    }
}
