package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long idTask;
    private Integer level;
    private Category category;
}