package com.guba.spring.speakappbackend.database.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tm_task_group")
@Setter
@Getter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task_group")
    private Long idTaskGroup;

    //@ManyToOne
    //@JoinColumn(name="id_phoneme", nullable=false)
    //private Phoneme phoneme;

    @OneToMany
    @JoinColumn(name = "id_task")
    private Set<TaskItem> taskItems;

    @ManyToOne
    @JoinColumn(name="id_patient", nullable=false)
    private Patient patient;

    @Column(name = "status")
    private String status;

    //@Column(name = "level")
    //private int level;

    //@Convert(converter = CategoryJpaConverter.class)
    //@Column(name = "category")
    //private Category category;
}
