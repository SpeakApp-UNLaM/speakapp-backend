package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.security.services.CustomUserDetailService;
import com.guba.spring.speakappbackend.storages.database.models.Professional;
import com.guba.spring.speakappbackend.storages.database.models.Role;
import com.guba.spring.speakappbackend.storages.database.repositories.ProfessionalRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.RoleRepository;
import com.guba.spring.speakappbackend.enums.RoleEnum;
import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.exceptions.NotSavedElementException;
import com.guba.spring.speakappbackend.security.dtos.SignUpDTO;
import com.guba.spring.speakappbackend.web.schemas.ProfessionalDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CustomUserDetailService customUserDetailService;

    public ProfessionalDTO saveProfessional(SignUpDTO signUpDTO) {

        final Professional professional = this.professionalRepository.findByUsernameOrEmail(signUpDTO.getUsername(), signUpDTO.getEmail());
        if (professional != null) {
            throw new UsernameNotFoundException("User exists with username or email");
        }

        String code;
        do {
            code = UUID.randomUUID().toString().replace("-","").substring(0,8).toUpperCase();
        } while (this.professionalRepository.findByCode(code).isPresent());

        final String passEncode = this.passwordEncoder.encode(signUpDTO.getPassword());
        final Role role = this.roleRepository.findByName(RoleEnum.PROFESSIONAL);
        final LocalDateTime now = LocalDateTime.now();

        return Optional
                .of(new Professional(signUpDTO, passEncode, code,  role, now, now))
                .map(this.professionalRepository::save)
                .map(ProfessionalDTO::create)
                .orElseThrow(() -> new NotSavedElementException("Not saved Professional " + signUpDTO));
    }

    public ProfessionalDTO updateProfessionalById(ProfessionalDTO professionalDTO) {
        final LocalDateTime updateAt = LocalDateTime.now();
        return Optional
                .ofNullable(this.customUserDetailService.getUserCurrent(Professional.class))
                .map(pOld -> new Professional(professionalDTO, pOld.getPassword(), pOld.getRole(), pOld.getCreatedAt(), updateAt, pOld.getPatients()))
                .map(this.professionalRepository::save)
                .map(ProfessionalDTO::create)
                .orElseThrow(() -> new NotSavedElementException("Not saved Professional " + professionalDTO));
    }

    public Set<ProfessionalDTO> getProfessionalAll() {
        return this.professionalRepository.findAll()
                .stream()
                .map(ProfessionalDTO::create)
                .collect(Collectors.toSet());
    }

    public ProfessionalDTO getProfessionalByCode(String code) {
        return this.professionalRepository.findByCode(code)
                .map(ProfessionalDTO::create)
                .orElseThrow(() -> new NotFoundElementException("Not found Professional for the code " + code));
    }

    public ProfessionalDTO getProfessionalById(Long idProfessional) {
        return this.professionalRepository.findById(idProfessional)
                .map(ProfessionalDTO::create)
                .orElseThrow(() -> new NotFoundElementException("Not found Professional for the id " + idProfessional));
    }

    public void removeProfessional(Long idProfessional) {
        this.professionalRepository.deleteById(idProfessional);
    }
}
