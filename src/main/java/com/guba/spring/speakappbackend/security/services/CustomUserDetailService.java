package com.guba.spring.speakappbackend.security.services;

import com.guba.spring.speakappbackend.security.dtos.LoginResponse;
import com.guba.spring.speakappbackend.storages.database.models.Patient;
import com.guba.spring.speakappbackend.storages.database.models.Professional;
import com.guba.spring.speakappbackend.storages.database.models.UserAbstract;
import com.guba.spring.speakappbackend.storages.database.repositories.PatientRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserAbstract user = getUser(username, username);

        Set<GrantedAuthority> authorities = Stream.of(user.getRole())
                .map(role -> new SimpleGrantedAuthority(role.getName().getName()))
                .collect(Collectors.toSet());

        return new User(username, user.getPassword(), authorities);
    }

    public LoginResponse getUserWithJWT(String usernameOrEmail) throws UsernameNotFoundException {
        final UserAbstract user = getUser(usernameOrEmail, usernameOrEmail);

        UserDetails userdetails = this.loadUserByUsername(usernameOrEmail);
        String token = jwtService.generateJwt(userdetails, user.getRole());

        return LoginResponse
                .builder()
                .idUser(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .imageData(user.getImageData())
                .token(token)
                .build();
    }

    public UserAbstract getUserCurrent() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(auth -> (UserDetails) auth.getPrincipal())
                .map(UserDetails::getUsername)
                .map(username -> this.getUser(username, null))
                .orElseThrow(() -> new IllegalArgumentException("The user current are not exist"));//throw Exception?
    }

    private UserAbstract getUser(String username, String email) {
        Patient patient = this.patientRepository.findByUsernameOrEmail(username, email);
        Professional professional = professionalRepository.findByUsernameOrEmail(username, email);
        if (patient == null && professional == null) {
            throw new UsernameNotFoundException("User not exists by Username or email");
        }
        final Long idUser = Optional
                .ofNullable(patient)
                .map(Patient::getIdPatient)
                .orElseGet(() -> professional.getIdProfessional());

        UserAbstract user = Optional
                .ofNullable((UserAbstract) patient)
                .orElse(professional);

        user.setId(idUser);
        return user;
    }

    public <T> T getUserCurrent(Class<T> tClass) {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(auth -> (UserDetails) auth.getPrincipal())
                .map(UserDetails::getUsername)
                .map(username -> this.getUser(username, null))
                .map(user -> {
                    if (tClass.equals(Professional.class)) {
                        Professional professional = (Professional) user;
                        professional.setIdProfessional(user.getId());
                        return tClass.cast(professional);
                    } else if (tClass.equals(Patient.class)) {
                        Patient patient = (Patient) user;
                        patient.setIdPatient(user.getId());
                        return tClass.cast(patient);
                    } else
                        return null;
                })
                .orElseThrow(() -> new IllegalArgumentException("The user current are not exist"));//throw Exception?
    }
}