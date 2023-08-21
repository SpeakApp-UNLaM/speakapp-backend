package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.database.models.Professional;
import com.guba.spring.speakappbackend.database.models.Role;
import com.guba.spring.speakappbackend.database.repositories.ProfessionalRepository;
import com.guba.spring.speakappbackend.database.repositories.RoleRepository;
import com.guba.spring.speakappbackend.enums.RoleEnum;
import com.guba.spring.speakappbackend.web.schemas.ProfessionalDTO;
import com.guba.spring.speakappbackend.security.dtos.SignUpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public ProfessionalDTO saveProfessional(SignUpDTO signUpDTO) {

        final Professional professional = this.professionalRepository.findByUsernameOrEmail(signUpDTO.getUsername(), signUpDTO.getEmail());
        if (professional != null) {
            throw new UsernameNotFoundException("User exists with username or email");
        }

        String code = UUID.randomUUID().toString().replace("-","").substring(0,8);
        while (this.professionalRepository.findByCode(code) != null) {
            code = UUID.randomUUID().toString().replace("-","").substring(0,8);
        }

        final Professional professionalSave = new Professional();
        final String passEncode = this.passwordEncoder.encode(signUpDTO.getPassword());
        final Role role = this.roleRepository.findByName(RoleEnum.PROFESSIONAL);
        final LocalDateTime now = LocalDateTime.now();


        professionalSave.setUsername(signUpDTO.getUsername());
        professionalSave.setEmail(signUpDTO.getEmail());
        professionalSave.setPassword(passEncode);
        professionalSave.setCode(code);
        professionalSave.setLastName(signUpDTO.getLastName());
        professionalSave.setFirstName(signUpDTO.getFirstName());
        professionalSave.setRole(role);
        professionalSave.setCreatedAt(now);
        professionalSave.setUpdatedAt(now);

        return Optional
                .of(this.professionalRepository.save(professionalSave))
                .map(p-> ProfessionalDTO
                        .builder()
                        .idProfessional(p.getIdProfessional())
                        .email(p.getEmail())
                        .username(p.getUsername())
                        .firstName(p.getFirstName())
                        .lastName(p.getLastName())
                        .age(p.getAge())
                        .imageData(p.getImageData())
                        .gender(p.getGender())
                        .build())
                .orElseThrow(IllegalArgumentException::new);
    }

    public ProfessionalDTO updateProfessional(ProfessionalDTO professionalDTO) {

        //TODO ADD id Professional
        Professional professional = new Professional(professionalDTO);

        return Optional
                .of(this.professionalRepository.save(professional))
                .map(p-> ProfessionalDTO
                        .builder()
                        .firstName(p.getFirstName())
                        .lastName(p.getLastName())
                        .age(p.getAge())
                        .build())
                .orElseThrow(IllegalArgumentException::new);
    }

    public ProfessionalDTO getProfessional(Long idProfessional) {
        return Optional
                .of(this.professionalRepository.getById(idProfessional))
                .map(p-> ProfessionalDTO
                        .builder()
                        .idProfessional(p.getIdProfessional())
                        .firstName(p.getFirstName())
                        .lastName(p.getLastName())
                        .age(p.getAge())
                        .code(p.getCode())
                        .email(p.getEmail())
                        .username(p.getUsername())
                        .gender(p.getGender())
                        //.patients(p.getPatients().stream().)
                        .build())
                .orElseThrow(IllegalArgumentException::new);
    }

    public void removeProfessional(Long idProfessional) {
        this.professionalRepository.deleteById(idProfessional);
    }
}
