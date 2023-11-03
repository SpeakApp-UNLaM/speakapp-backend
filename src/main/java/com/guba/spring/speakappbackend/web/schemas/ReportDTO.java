package com.guba.spring.speakappbackend.web.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.guba.spring.speakappbackend.storages.database.models.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    @NotEmpty(message = "The title is required.")
    @NotNull
    private String title;
    @NotEmpty(message = "The body is required.")
    @NotNull
    private String body;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    public ReportDTO(Report r) {
        this.body = r.getBody();
        this.title = r.getTitle();
        this.createdAt = r.getCreatedAt();
    }
}
