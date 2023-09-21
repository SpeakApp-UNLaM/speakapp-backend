package com.guba.spring.speakappbackend.web.schemas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultExerciseDTO {
    private Long idTaskItem;
    private String audio;
    private List<SelectionImage> selectionImages;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SelectionImage {
        private Long idImage;
        private String name;
    }
}
