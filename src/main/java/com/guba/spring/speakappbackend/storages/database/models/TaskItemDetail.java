package com.guba.spring.speakappbackend.storages.database.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tm_task_item_detail")
@Setter
@Getter
@NoArgsConstructor
public class TaskItemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task_item_detail")
    private Long idTaskItemDetail;

    @Column(name = "id_image_selected")
    private Long idImageSelected;

    @Column(name = "id_task_item")
    private Long idTaskItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_task_item", insertable=false, updatable=false)
    private TaskItem taskItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_image_selected", referencedColumnName = "id_image", insertable=false, updatable=false)
    private Image imageSelected;

    @Column(name = "result_selected")
    private String resultSelected;

    public TaskItemDetail(Long idImageSelected, Long idTaskItem, String resultSelected) {
        this.idImageSelected = idImageSelected;
        this.idTaskItem = idTaskItem;
        this.resultSelected = resultSelected;
    }
}
