package com.guba.spring.speakappbackend.storages.database.models;

import com.guba.spring.speakappbackend.storages.database.converters.CategoryJpaConverter;
import com.guba.spring.speakappbackend.storages.database.converters.TaskStatusJpaConverter;
import com.guba.spring.speakappbackend.enums.Category;
import com.guba.spring.speakappbackend.enums.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @ManyToOne
    @JoinColumn(name="id_patient", nullable=false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="id_professional", nullable=false)
    private Professional professional;

    @ManyToOne
    @JoinColumn(name="id_phoneme", nullable=false)
    private Phoneme phoneme;

    @Column(name = "level")
    private int level;

    @Convert(converter = CategoryJpaConverter.class)
    @Column(name = "category")
    private Category category;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime startDate;

    @Column(name = "end_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime endDate;

    @Convert(converter = TaskStatusJpaConverter.class)
    @Column(name = "status")
    private TaskStatus status;

    @OneToMany(mappedBy="task")
    private Set<TaskItem> taskItems;
}
