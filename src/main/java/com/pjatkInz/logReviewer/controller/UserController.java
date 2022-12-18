package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.configuration.JwtUtil;
import com.pjatkInz.logReviewer.dto.AuthenticationRequest;
import com.pjatkInz.logReviewer.dto.AuthenticationResponse;
import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.service.MyUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;

    public UserController(AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest authenticationRequest){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        }catch(BadCredentialsException ex){
            throw new RuntimeException("Username or password incorrect");
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse("Bearer " + token));

    }
    @PostMapping("/register")
    public ResponseEntity<UUID> addUser(@Valid @RequestBody UserDto userDto){
        UUID uuid = myUserDetailsService.addUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(uuid);
    }
}
