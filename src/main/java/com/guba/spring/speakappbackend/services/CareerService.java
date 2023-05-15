package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.models.Career;
import com.guba.spring.speakappbackend.repositories.CareerRepository;
import com.guba.spring.speakappbackend.schemas.CareerDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CareerService {

    private final CareerRepository careerRepository;

    @Autowired
    public CareerService(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    public List<CareerDTO> getCareers() {
        log.info("obtener Carrers de la bbdd");
        return careerRepository.findAll()
                .stream()
                .map(CareerDTO::new)
                .collect(Collectors.toList());
    }

    public List<CareerDTO> getCareersByName(String name) {
        log.info("obtener Carrers por nombre de la bbdd");
        return careerRepository.getCareerByName(name)
                .stream()
                .map(CareerDTO::new)
                .collect(Collectors.toList());
    }

    public CareerDTO saveCareer(CareerDTO careerDTO) {
        log.info("crear Carrer en la bbdd");
        var career = new Career(careerDTO);
        var careerUpdate =  this.careerRepository.save(career);
        return new CareerDTO(careerUpdate);
    }
}
