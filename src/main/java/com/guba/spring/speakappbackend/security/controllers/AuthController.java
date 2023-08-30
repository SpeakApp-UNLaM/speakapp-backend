package com.guba.spring.speakappbackend.security.controllers;

import com.guba.spring.speakappbackend.exceptions.AuthenticationException;
import com.guba.spring.speakappbackend.security.services.JwtService;
import com.guba.spring.speakappbackend.security.services.CustomUserDetailService;
import com.guba.spring.speakappbackend.security.dtos.AuthenticationResponse;
import com.guba.spring.speakappbackend.security.dtos.LoginDTO;
import com.guba.spring.speakappbackend.security.dtos.SignUpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody @Valid LoginDTO loginDTO) {

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            UserDetails userdetails = customUserDetailService.loadUserByUsername(loginDTO.getUsername());
            String token = jwtService.generateJwt(userdetails);
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (DisabledException e) {
            throw new AuthenticationException("the user is disabled", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("the credentials are invalid", e);
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody @Valid SignUpDTO signUpDTO){
        customUserDetailService.save(signUpDTO);
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.CREATED);
    }
}
