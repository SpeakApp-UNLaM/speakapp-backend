package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.repositories.database.models.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Long idImage;
    private String name;
    private String imageData;
    private String dividedName;

    public ImageDTO(Image image) {
        this.idImage = image.getIdImage();
        this.name = image.getName();
        this.imageData = image.getImageData();
        this.dividedName = image.getDividedName();
    }
}
