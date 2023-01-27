package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.model.PermissionsChange;
import com.pjatkInz.logReviewer.service.PermissionsChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${my-api.version}"+"${my-api.permissionsChange}")
public class PermissionsChangeController {

    @Autowired
    private PermissionsChangeService permissionsChangeService;

    @GetMapping
    @PreAuthorize("hasAnyRole('REVIEWER','REVIEWER_MANAGER','REVIEWED_ISA')")
    public ResponseEntity<List<PermissionsChangeDto>> getPermissionsChanges(){
        List<PermissionsChangeDto> permissionsChangeDtoList = permissionsChangeService.getPermissionsChanges();
        return ResponseEntity.ok(permissionsChangeDtoList);
    }


}
