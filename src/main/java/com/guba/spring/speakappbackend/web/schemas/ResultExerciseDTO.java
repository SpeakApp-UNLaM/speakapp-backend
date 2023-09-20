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
    private List<ImageResultDTO> imagesResultDTO;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor//SelectionImage
    public static class ImageResultDTO {
        private Long id;
        private String nameAudio;
    }
}
