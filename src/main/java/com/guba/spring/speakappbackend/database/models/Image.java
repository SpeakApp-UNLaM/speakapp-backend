package com.guba.spring.speakappbackend.database.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "image_data", columnDefinition = "TEXT")
    private String imageData;

    @Column(name = "name")
    private String name;

    @Column(name = "divided_name")
    private String dividedName;
}
