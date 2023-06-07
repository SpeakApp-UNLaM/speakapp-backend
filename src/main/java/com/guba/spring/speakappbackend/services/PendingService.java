package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.models.Career;
import com.guba.spring.speakappbackend.models.Pending;
import com.guba.spring.speakappbackend.repositories.CareerRepository;
import com.guba.spring.speakappbackend.repositories.PendingRepository;
import com.guba.spring.speakappbackend.schemas.CareerDTO;
import com.guba.spring.speakappbackend.schemas.PendingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PendingService {

    private final PendingRepository pendingRepository;

    @Autowired
    public PendingService(PendingRepository pendingRepository) {
        this.pendingRepository = pendingRepository;
    }

    public List<PendingDTO> getPending() {
        log.info("obtener Carrers de la bbdd");
        return pendingRepository.findAll()
                .stream()
                .map(PendingDTO::new)
                .collect(Collectors.toList());
    }

    public List<PendingDTO> getPendingByName(int name) {
        log.info("obtener Carrers por nombre de la bbdd");
        return pendingRepository.getPendingByName(name)
                .stream()
                .map(PendingDTO::new)
                .collect(Collectors.toList());
    }


    public PendingDTO savePending(PendingDTO pendingDTO) {
        log.info("crear Pending en la bbdd");
        var pending = new Pending(pendingDTO);
        var pendingUpdate =  this.pendingRepository.save(pending);
        return new PendingDTO(pendingUpdate);
    }
}
