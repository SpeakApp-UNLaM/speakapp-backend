package com.guba.spring.speakappbackend.security.services;

import com.guba.spring.speakappbackend.repositories.database.models.Patient;
import com.guba.spring.speakappbackend.repositories.database.models.Professional;
import com.guba.spring.speakappbackend.repositories.database.models.Role;
import com.guba.spring.speakappbackend.repositories.database.models.UserAbstract;
import com.guba.spring.speakappbackend.repositories.database.repositories.PatientRepository;
import com.guba.spring.speakappbackend.repositories.database.repositories.ProfessionalRepository;
import com.guba.spring.speakappbackend.enums.RoleEnum;
import com.guba.spring.speakappbackend.security.dtos.LoginResponse;
import com.guba.spring.speakappbackend.services.PatientService;
import com.guba.spring.speakappbackend.services.ProfessionalService;
import com.guba.spring.speakappbackend.security.dtos.SignUpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;
    private final PatientService patientService;
    private final ProfessionalService professionalService;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient patient = this.patientRepository.findByUsernameOrEmail(username, username);
        Professional professional = professionalRepository.findByUsernameOrEmail(username, username);
        if (patient == null && professional == null) {
            throw new UsernameNotFoundException("User not exists by Username or email");
        }
        final Role roleUser = Optional
                .ofNullable(patient)
                .map(UserAbstract::getRole)
                .orElseGet(() -> professional.getRole());

        final String passwordUser = Optional
                .ofNullable(patient)
                .map(UserAbstract::getPassword)
                .orElseGet(() -> professional.getPassword());

        Set<GrantedAuthority> authorities = Stream.of(roleUser)
                .map(role -> new SimpleGrantedAuthority(role.getName().getName()))
                .collect(Collectors.toSet());

        return new User(username, passwordUser, authorities);
    }

    public LoginResponse getUserWithJWT(String usernameOrEmail) throws UsernameNotFoundException {
        Patient patient = this.patientRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        Professional professional = professionalRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (patient == null && professional == null) {
            throw new UsernameNotFoundException("User not exists by Username or email");
        }
        UserDetails userdetails = this.loadUserByUsername(usernameOrEmail);
        String token = jwtService.generateJwt(userdetails);

        final Long idUser = Optional
                .ofNullable(patient)
                .map(Patient::getIdPatient)
                .orElseGet(() -> professional.getIdProfessional());
        final UserAbstract user = Optional
                .ofNullable((UserAbstract) patient)
                .orElse(professional);

        return LoginResponse
                .builder()
                .idUser(idUser)
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .imageData(user.getImageData())
                .token(token)
                .build();
    }

    public void save(SignUpDTO signUpDTO) {

        if (signUpDTO.getType() == RoleEnum.PROFESSIONAL) {
            this.professionalService.saveProfessional(signUpDTO);
        } else if (signUpDTO.getType() == RoleEnum.PATIENT){
            this.patientService.savePatient(signUpDTO);
        } else {
            throw new IllegalArgumentException("the type no is professional o patient");
        }
    }
}