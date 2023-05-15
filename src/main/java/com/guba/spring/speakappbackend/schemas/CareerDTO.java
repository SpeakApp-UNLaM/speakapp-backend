package com.guba.spring.speakappbackend.schemas;

import com.guba.spring.speakappbackend.models.Career;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CareerDTO {

    private String name;
    private String description;

    public CareerDTO(Career c) {
        this.description = c.getDescription();
        this.name = c.getName();
    }
}
