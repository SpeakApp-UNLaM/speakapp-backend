package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.database.models.Patient;
import com.guba.spring.speakappbackend.database.models.Role;
import com.guba.spring.speakappbackend.database.repositories.PatientRepository;
import com.guba.spring.speakappbackend.database.repositories.RoleRepository;
import com.guba.spring.speakappbackend.enums.RoleEnum;
import com.guba.spring.speakappbackend.web.schemas.PatientDTO;
import com.guba.spring.speakappbackend.security.dtos.SignUpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public PatientDTO savePatient(SignUpDTO signUpDTO) {
        final Patient patient = this.patientRepository.findByUsernameOrEmail(signUpDTO.getUsername(), signUpDTO.getEmail());
        if(patient != null) {
            throw new UsernameNotFoundException("User exists with username or email");
        }
        final String passEncode = passwordEncoder.encode(signUpDTO.getPassword());
        final Role role = roleRepository.findByName(RoleEnum.PATIENT);
        final LocalDateTime now = LocalDateTime.now();

        Patient patientSave = new Patient();
        patientSave.setUsername(signUpDTO.getUsername());
        patientSave.setEmail(signUpDTO.getEmail());
        patientSave.setPassword(passEncode);
        patientSave.setLastName(signUpDTO.getLastName());
        patientSave.setFirstName(signUpDTO.getFirstName());
        patientSave.setRole(role);
        patientSave.setCreatedAt(now);
        patientSave.setUpdatedAt(now);

        Patient patientSaved = this.patientRepository.save(patientSave);

        return PatientDTO
                .builder()
                .idPatient(patientSaved.getIdPatient())
                .email(patientSaved.getEmail())
                .username(patientSaved.getUsername())
                .firstName(patientSaved.getFirstName())
                .lastName(patientSaved.getLastName())
                .age(patientSaved.getAge())
                .imageData(patientSaved.getImageData())
                .gender(patientSaved.getGender())
                .build();
    }

    public PatientDTO updatePatient(PatientDTO patientDTO) {
        //TODO OBTENER EL Patient DE LA SESSION
        Patient patient = new Patient();

        patient.setFirstName(patient.getFirstName());
        patient.setLastName(patient.getLastName());
        patient.setAge(patient.getAge());

        Patient patientUpdated = this.patientRepository.save(patient);

        return PatientDTO
                .builder()
                .firstName(patientUpdated.getFirstName())
                .lastName(patientUpdated.getLastName())
                .age(patientUpdated.getAge())
                .build();
    }

    public PatientDTO getPatient(Long idPatient) {
        Patient patient = this.patientRepository.getById(idPatient);

        return PatientDTO
                .builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .age(patient.getAge())
                .build();
    }

    public void removePatient(Long idPatient) {
         this.patientRepository.deleteById(idPatient);
    }

}
