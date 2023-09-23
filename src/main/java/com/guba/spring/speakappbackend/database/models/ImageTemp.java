package com.guba.spring.speakappbackend.database.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "tm_image_temp")
@Setter
@Getter
@NoArgsConstructor
public class ImageTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private Long idImage;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "name")
    private String name;
}
