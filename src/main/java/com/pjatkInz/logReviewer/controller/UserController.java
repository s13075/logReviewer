package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.configuration.JwtUtil;
import com.pjatkInz.logReviewer.dto.AuthenticationRequest;
import com.pjatkInz.logReviewer.dto.AuthenticationResponse;
import com.pjatkInz.logReviewer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
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
        UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse("Bearer " + token));

    }
}
