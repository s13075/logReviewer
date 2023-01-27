package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.PermissionsRequestDto;
import com.pjatkInz.logReviewer.service.PermissionsChangeService;
import com.pjatkInz.logReviewer.service.PermissionsRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${my-api.version}"+"${my-api.permissionsRequest}")
public class PermissionsRequestController {

    @Autowired
    private PermissionsRequestService permissionsRequestService;

    @GetMapping
    @PreAuthorize("hasAnyRole('REVIEWER','REVIEWER_MANAGER','REVIEWED_ISA')")
    public ResponseEntity<List<PermissionsRequestDto>> getPermissionsRequests(){
        List<PermissionsRequestDto> permissionsRequestDtoList = permissionsRequestService.getPermissionsRequests();
        return ResponseEntity.ok(permissionsRequestDtoList);
    }
}
