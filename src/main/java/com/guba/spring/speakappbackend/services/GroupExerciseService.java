package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.models.GroupExercise;
import com.guba.spring.speakappbackend.models.Pending;
import com.guba.spring.speakappbackend.repositories.GroupExerciseRepository;
import com.guba.spring.speakappbackend.repositories.PendingRepository;
import com.guba.spring.speakappbackend.schemas.GroupExerciseDTO;
import com.guba.spring.speakappbackend.schemas.PendingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroupExerciseService {

    private final GroupExerciseRepository groupExerciseRepository;

    @Autowired
    public GroupExerciseService(GroupExerciseRepository groupExerciseRepository) {
        this.groupExerciseRepository = groupExerciseRepository;
    }

    public List<GroupExerciseDTO> getGroupExercise() {
        log.info("obtener Carrers de la bbdd");
        return groupExerciseRepository.findAll()
                .stream()
                .map(GroupExerciseDTO::new)
                .collect(Collectors.toList());
    }

    public List<GroupExerciseDTO> getGroupExerciseByName(int name) {
        log.info("obtener Carrers por nombre de la bbdd");
        return groupExerciseRepository.getGroupExerciseByName(name)
                .stream()
                .map(GroupExerciseDTO::new)
                .collect(Collectors.toList());
    }


    public GroupExerciseDTO saveGroupExercise(GroupExerciseDTO groupExerciseDTO) {
        log.info("crear Pending en la bbdd");
        var groupExercise = new GroupExercise(groupExerciseDTO);
        var groupExerciseUpdate =  this.groupExerciseRepository.save(groupExercise);
        return new GroupExerciseDTO(groupExerciseUpdate);
    }
}
