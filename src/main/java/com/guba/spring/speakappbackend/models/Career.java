package com.guba.spring.speakappbackend.models;

import com.guba.spring.speakappbackend.schemas.CareerDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "career")
@Setter
@Getter
@NoArgsConstructor
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Career(CareerDTO c) {
        this.description = c.getDescription();
        this.name = c.getName();
    }
}