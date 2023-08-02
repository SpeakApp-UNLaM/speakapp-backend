package com.guba.spring.speakappbackend.models;


import com.guba.spring.speakappbackend.enums.Category;
import com.guba.spring.speakappbackend.converters.CategoryJpaConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tm_exercise")
@Setter
@Getter
@NoArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exercise")
    private Long idExercise;

    @Column(name = "result_expected")
    private String resultExpected;

    @Column(name = "level")
    private int level;

    //@Enumerated(EnumType.STRING)
    @Convert(converter = CategoryJpaConverter.class)
    @Column(name = "category")
    private Category category;

    @Column(name = "type")
    private String type;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @JoinTable(name = "tm_image_exercise",
            joinColumns=@JoinColumn(name="id_exercise"),
            inverseJoinColumns=@JoinColumn(name="id_image"))
    private List<Image> images;
    //data

}
