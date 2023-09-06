package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.database.models.Phoneme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhonemeDTO {

    private Long idPhoneme;
    private String namePhoneme;

    public PhonemeDTO(Phoneme p) {
        this.idPhoneme = p.getIdPhoneme();
        this.namePhoneme = p.getNamePhoneme();
    }
}
