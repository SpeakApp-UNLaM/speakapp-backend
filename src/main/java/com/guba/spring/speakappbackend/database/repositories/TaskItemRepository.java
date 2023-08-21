package com.guba.spring.speakappbackend.database.repositories;

import com.guba.spring.speakappbackend.database.models.TaskItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskItemRepository extends JpaRepository<TaskItem, Long> {

}