package com.guba.spring.speakappbackend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "tm_image")
@Setter
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private Long idImage;

    //@Lob
    //@Type(type = "org.hibernate.type.ImageType")
    @Column(name = "image_data", columnDefinition = "TEXT")
    private String imageData;

    @Column(name = "name")
    private String name;
}
