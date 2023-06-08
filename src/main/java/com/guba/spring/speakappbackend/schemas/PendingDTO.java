package com.guba.spring.speakappbackend.schemas;

import com.guba.spring.speakappbackend.models.Career;
import com.guba.spring.speakappbackend.models.Pending;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PendingDTO {
    private int idUsr;
    private int idExercise;
    private int idGroupExercise;

    public PendingDTO(Pending c) {
        this.idUsr = c.getIdUsr();
        this.idExercise = c.getIdExercise();
        this.idGroupExercise = c.getIdGroupExercise();
    }

}
