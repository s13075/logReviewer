package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{title}")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByName(@PathVariable("title") String title) {
        List<ApplicationDto> applications = applicationService.getApplicationsByName(title);
        return ResponseEntity.ok(applications);
    }
}
