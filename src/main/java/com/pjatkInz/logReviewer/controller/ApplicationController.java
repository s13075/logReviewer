package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.PermissionsRequestDto;
import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.service.ApplicationService;
import com.pjatkInz.logReviewer.service.MyUserDetailsService;
import com.pjatkInz.logReviewer.service.PermissionsChangeService;
import com.pjatkInz.logReviewer.service.PermissionsRequestService;
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
    @Autowired
    private PermissionsChangeService permissionsChangeService;
    @Autowired
    private PermissionsRequestService permissionsRequestService;
    @GetMapping
    @PreAuthorize("hasAnyRole('REVIEWER','REVIEWER_MANAGER')")
    public ResponseEntity<List<ApplicationDto>> getApplications(){
        List<ApplicationDto> applications = applicationService.getApplications();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{title}")
    @PreAuthorize("hasAnyRole('REVIEWER','REVIEWER_MANAGER')")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByName(@PathVariable("title") String title) {
        List<ApplicationDto> applications = applicationService.getApplicationsByName(title);
        return ResponseEntity.ok(applications);
    }


    @GetMapping("/{id}/reviewers")
    @PreAuthorize("hasAnyRole('REVIEWER','REVIEWER_MANAGER')")
    public ResponseEntity<List<UserDto>> getApplicationReviewers(@PathVariable("id") String id) {
        List<UserDto> users = applicationService.getApplicationReviewers(id);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/permissionsChange")
    @PreAuthorize("hasAnyRole('REVIEWER','REVIEWER_MANAGER')")
    public ResponseEntity<List<PermissionsChangeDto>> getApplicationPermissionsChanges(@PathVariable("id") String id) {
        List<PermissionsChangeDto> permissionsChanges = permissionsChangeService.getApplicationChanges(id);
        return ResponseEntity.ok(permissionsChanges);
    }


    @GetMapping("/{id}/permissionsRequest")
    @PreAuthorize("hasAnyRole('REVIEWER','REVIEWER_MANAGER')")
    public ResponseEntity<List<PermissionsRequestDto>> getApplicationPermissionsRequests(@PathVariable("id") String id) {
        List<PermissionsRequestDto> permissionsRequests = permissionsRequestService.getApplicationRequests(id);
        return ResponseEntity.ok(permissionsRequests);
    }
}
