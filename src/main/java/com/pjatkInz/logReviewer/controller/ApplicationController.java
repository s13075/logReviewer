package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(("api/v1/applications"))
public class ApplicationController {

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getApplications(){

        ApplicationDto applicationDto = ApplicationDto.builder()
                .name("firstApp")
                .build();
        List<ApplicationDto> applications = new ArrayList<>();
        applications.add(applicationDto);

        return ResponseEntity.ok(applications);
    }
}
