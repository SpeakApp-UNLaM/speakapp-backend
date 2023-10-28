package com.guba.spring.speakappbackend.web.schemas;

import lombok.*;

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
    @ToString
    public static class SelectionImage {
        private Long idImage;
        private String name;
    }

    @Override
    public String toString() {
        return "ResultExerciseDTO{" +
                "idTaskItem=" + idTaskItem +
                ", selectionImages=" + selectionImages +
                '}';
    }
}
