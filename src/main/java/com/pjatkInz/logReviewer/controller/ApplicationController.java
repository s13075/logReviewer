package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.service.ApplicationService;
import com.pjatkInz.logReviewer.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${my-api.version}"+"${my-api.applications}")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;



    @GetMapping
    @PreAuthorize("hasRole('REVIEWER')")
    public ResponseEntity<List<ApplicationDto>> getApplications(){
        List<ApplicationDto> applications = applicationService.getApplications();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{title}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByName(@PathVariable("title") String title) {
        List<ApplicationDto> applications = applicationService.getApplicationsByName(title);
        return ResponseEntity.ok(applications);
    }


    @GetMapping("/{id}/reviewers")
    @PreAuthorize("hasRole('REVIEWER')")
    public ResponseEntity<List<UserDto>> getApplicationReviewers(@PathVariable("id") String id) {
        List<UserDto> users = applicationService.getApplicationReviewers(id);
        return ResponseEntity.ok(users);
    }
}
