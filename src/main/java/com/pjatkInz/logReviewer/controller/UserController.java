package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.configuration.JwtUtil;
import com.pjatkInz.logReviewer.dto.AuthenticationRequest;
import com.pjatkInz.logReviewer.dto.AuthenticationResponse;
import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.model.MyUserDetails;
import com.pjatkInz.logReviewer.service.MyUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()));

        }catch(BadCredentialsException ex){
            throw new RuntimeException("Username or password incorrect");
        }
        MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        UserDto user = myUserDetailsService.convertUserToUserDto().apply(userDetails.getUser());
        user.setPassword("***");
        return ResponseEntity.ok(new AuthenticationResponse("Bearer " + token, user));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto){

        UserDto createdUserDto = myUserDetailsService.addUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> listUsers(){
        List<UserDto> users = myUserDetailsService.getUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/user/{emploeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserByEmploeeId(@PathVariable("emploeeId") String emploeeId){
        UserDto user = myUserDetailsService.getUserByEmploeeId(emploeeId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/{emploeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUserByEmploeeId(@PathVariable("emploeeId") String emploeeId, @Valid @RequestBody UserDto userDto){
        UserDto updatedEmploee = myUserDetailsService.updateUser(emploeeId, userDto);

        return ResponseEntity.ok(updatedEmploee);
    }
}
