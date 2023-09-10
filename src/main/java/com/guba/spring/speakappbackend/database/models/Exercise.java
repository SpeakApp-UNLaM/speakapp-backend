package com.guba.spring.speakappbackend.database.models;


import com.guba.spring.speakappbackend.database.converters.TypeExerciseJpaConverter;
import com.guba.spring.speakappbackend.enums.Category;
import com.guba.spring.speakappbackend.database.converters.CategoryJpaConverter;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tm_exercise")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Convert(converter = TypeExerciseJpaConverter.class)
    @Column(name = "type")
    private TypeExercise type;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @JoinTable(name = "tm_image_exercise",
            joinColumns=@JoinColumn(name="id_exercise"),
            inverseJoinColumns=@JoinColumn(name="id_image"))
    private Set<Image> images;

    @ManyToOne
    @JoinColumn(name="id_phoneme", nullable=false)
    private Phoneme phoneme;

}
