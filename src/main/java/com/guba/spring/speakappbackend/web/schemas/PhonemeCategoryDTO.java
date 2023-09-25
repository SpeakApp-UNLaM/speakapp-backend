package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.repositories.database.models.Phoneme;
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
public class PhonemeCategoryDTO {

    private PhonemeDTO phoneme;
    private Set<CategoryDTO> categoriesDTO;

    public PhonemeCategoryDTO(Phoneme p, Set<CategoryDTO> categoriesDTO) {
        this.phoneme = new PhonemeDTO(p);
        this.categoriesDTO = categoriesDTO;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDTO {
        private Long idTask;
        private Category category;
        private int level;
    }
}
