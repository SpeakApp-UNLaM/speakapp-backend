package com.guba.spring.speakappbackend.schemas;

import com.guba.spring.speakappbackend.models.GroupExercise;
import com.guba.spring.speakappbackend.models.Pending;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupExerciseDTO {
    private String wordGroupExercise;
    private long id;
    public GroupExerciseDTO(GroupExercise c) {
        this.id = c.getId();
        this.wordGroupExercise = c.getWordGroupExercise();
    }

}
