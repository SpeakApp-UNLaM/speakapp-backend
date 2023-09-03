package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.database.models.Phoneme;
import com.guba.spring.speakappbackend.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhonemeDTO {

    private Long idPhoneme;
    private String phoneme;

    private Set<CategoryDTO> categoriesDTO;

    public PhonemeDTO(Phoneme p) {
        this.idPhoneme = p.getIdPhoneme();
        this.phoneme = p.getPhoneme();
        this.categoriesDTO = Set.of();
    }

    public PhonemeDTO(Phoneme p, Set<CategoryDTO> categoriesDTO) {
        this.idPhoneme = p.getIdPhoneme();
        this.phoneme = p.getPhoneme();
        this.categoriesDTO = categoriesDTO;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDTO {
        private Category category;
        private int level;
    }
}
