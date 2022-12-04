package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(("api/v1/applications"))
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;


    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getApplications(){
        List<ApplicationDto> applications = applicationService.getApplications();
        return ResponseEntity.ok(applications);
    }
}
