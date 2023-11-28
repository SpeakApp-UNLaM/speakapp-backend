package com.guba.spring.speakappbackend.security.controllers;

import com.guba.spring.speakappbackend.enums.RoleEnum;
import com.guba.spring.speakappbackend.exceptions.AuthenticationException;
import com.guba.spring.speakappbackend.security.services.CustomUserDetailService;
import com.guba.spring.speakappbackend.security.dtos.LoginResponse;
import com.guba.spring.speakappbackend.security.dtos.LoginDTO;
import com.guba.spring.speakappbackend.security.dtos.SignUpDTO;
import com.guba.spring.speakappbackend.services.PatientService;
import com.guba.spring.speakappbackend.services.ProfessionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class AuthController {

    private final CustomUserDetailService customUserDetailService;
    private final PatientService patientService;
    private final ProfessionalService professionalService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody @Valid LoginDTO loginDTO) {

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            LoginResponse response = customUserDetailService.getUserWithJWT(loginDTO.getUsername());
            return ResponseEntity.ok(response);
        } catch (DisabledException e) {
            throw new AuthenticationException("the user is disabled", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("the credentials are invalid", e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody @Valid SignUpDTO signUpDTO){
        if (signUpDTO.getType() == RoleEnum.PROFESSIONAL) {
            this.professionalService.saveProfessional(signUpDTO);
        } else if (signUpDTO.getType() == RoleEnum.PATIENT){
            this.patientService.savePatient(signUpDTO);
        } else {
            throw new IllegalArgumentException("the type no is professional o patient");
        }
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.CREATED);
    }
}
