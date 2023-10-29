package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.enums.RoleEnum;
import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.exceptions.NotSavedElementException;
import com.guba.spring.speakappbackend.security.dtos.SignUpDTO;
import com.guba.spring.speakappbackend.security.services.CustomUserDetailService;
import com.guba.spring.speakappbackend.storages.database.models.Patient;
import com.guba.spring.speakappbackend.storages.database.models.Professional;
import com.guba.spring.speakappbackend.storages.database.models.Role;
import com.guba.spring.speakappbackend.storages.database.repositories.PatientRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.RoleRepository;
import com.guba.spring.speakappbackend.web.schemas.PatientDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CustomUserDetailService customUserDetailService;

    public PatientDTO savePatient(SignUpDTO signUpDTO) {
        final Patient patient = this.patientRepository.findByUsernameOrEmail(signUpDTO.getUsername(), signUpDTO.getEmail());
        if(patient != null) {
            throw new UsernameNotFoundException("User exists with username or email");
        }
        final String passEncode = passwordEncoder.encode(signUpDTO.getPassword());
        final Role role = roleRepository.findByName(RoleEnum.PATIENT);
        final LocalDateTime now = LocalDateTime.now();

        return Optional
                .of(new Patient(signUpDTO, passEncode, role, now, now, null))
                .map(this.patientRepository::save)
                .map(PatientDTO::create)
                .orElseThrow(() -> new NotSavedElementException("Not saved Patient " + signUpDTO));
    }

    public PatientDTO updatePatient(PatientDTO patientDTO) {
        final Professional professional = Optional
                .ofNullable(this.customUserDetailService.getUserCurrent(Patient.class))
                .map(Patient::getProfessional)
                .orElse(null);
        final LocalDateTime updateAt = LocalDateTime.now();

        return this.patientRepository
                .findById(patientDTO.getIdPatient())
                .map(pOld -> new Patient(patientDTO, pOld.getPassword(), pOld.getRole(), pOld.getCreatedAt(), updateAt, professional))
                .map(this.patientRepository::save)
                .map(PatientDTO::create)
                .orElseThrow(() -> new NotSavedElementException("Not saved Patient " + patientDTO));
    }

    public Set<PatientDTO> getPatientAll() {
        return Optional
                .ofNullable(this.customUserDetailService.getUserCurrent(Professional.class))
                .stream()
                .flatMap(p -> p.getPatients().stream())
                .map(PatientDTO::create)
                .collect(Collectors.toSet())
                ;
    }

    public PatientDTO getPatient(Long idPatient) {
        return this.patientRepository
                .findById(idPatient)
                .map(PatientDTO::create)
                .orElseThrow(() -> new NotFoundElementException("Not found patient for the id " + idPatient));
    }

    public void removePatient(Long idPatient) {
         this.patientRepository.deleteById(idPatient);
    }

}
