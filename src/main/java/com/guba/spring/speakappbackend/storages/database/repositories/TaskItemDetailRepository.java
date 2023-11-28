package com.guba.spring.speakappbackend.storages.database.repositories;

import com.guba.spring.speakappbackend.storages.database.models.TaskItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TaskItemDetailRepository extends JpaRepository<TaskItemDetail, Long> {

    //TODO REVISAR POR QUE NO FUNCIONA
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteTaskItemDetailsByIdTaskItem(Long idTaskItem);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tm_task_item_detail as detail where detail.id_task_item = ?1",nativeQuery = true)
    void deleteByIdTaskItem(Long idTaskItem);
}
