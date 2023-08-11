package com.guba.spring.speakappbackend.schemas;

import com.guba.spring.speakappbackend.models.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private String name;
    private String imageData;
    private String dividedName;

    public ImageDTO(Image image) {
        this.name = image.getName();
        this.imageData = image.getImageData();
    }
}
