package com.guba.spring.speakappbackend.database.models;

import com.guba.spring.speakappbackend.database.converters.TaskStatusJpaConverter;
import com.guba.spring.speakappbackend.enums.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Convert(converter = TaskStatusJpaConverter.class)
    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "start_date", columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "DATE")
    private LocalDate endDate;

    //@Column(name = "level")
    //private int level;

    //@Convert(converter = CategoryJpaConverter.class)
    //@Column(name = "category")
    //private Category category;
}
