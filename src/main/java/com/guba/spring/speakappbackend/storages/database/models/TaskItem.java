package com.guba.spring.speakappbackend.storages.database.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tm_task_group_detail")
@Setter
@Getter
@NoArgsConstructor
public class TaskItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task")
    private Long idTask;

    @ManyToOne
    @JoinColumn(name = "id_task_group", nullable=false)
    private Task task;

    @ManyToOne
    @JoinColumn(name="id_exercise", nullable=false)
    private Exercise exercise;

    @Column(name = "url_audio")
    private String urlAudio;

    @Column(name = "result")
    private String result;

    public TaskItem(Task task, Exercise exercise, String urlAudio, String result) {
        this.task = task;
        this.exercise = exercise;
        this.urlAudio = urlAudio;
        this.result = result;
    }
}
