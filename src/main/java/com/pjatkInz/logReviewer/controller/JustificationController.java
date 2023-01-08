package com.pjatkInz.logReviewer.controller;


import com.pjatkInz.logReviewer.dto.JustificationDto;
import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.ReconciliationDto;
import com.pjatkInz.logReviewer.service.JustificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${my-api.version}"+"${my-api.justification}")
public class JustificationController {

    @Autowired
    private JustificationService justificationService;

    @PutMapping("/{justificationId}")
    @PreAuthorize("hasRole('REVIEWER')")
    public ResponseEntity<JustificationDto> updateJustification(@PathVariable("justificationId") UUID justificationId, @Valid @RequestBody JustificationDto justificationDto){
        JustificationDto justificationAfterUpdate = justificationService.updateJustification(justificationId, justificationDto);
        return ResponseEntity.ok(justificationAfterUpdate);
    }

    @GetMapping
    @PreAuthorize("hasRole('REVIEWER') OR hasRole('REVIEWER_MANAGER') OR hasRole('REVIEWED_ISA')")
    public ResponseEntity<List<JustificationDto>> getJustification(){
        List<JustificationDto> justifications = justificationService.getJustifications();
        return ResponseEntity.ok(justifications);
    }

    @GetMapping("/{justificationId}/permissionChanges")
    @PreAuthorize("hasRole('REVIEWER') OR hasRole('REVIEWER_MANAGER') OR hasRole('REVIEWED_ISA')")
    public ResponseEntity<List<PermissionsChangeDto>> getJustification(@PathVariable("justificationId") UUID justificationId){
        List<PermissionsChangeDto> permissionChanges = justificationService.getPermissionChangesByJustificationId(justificationId);
        return ResponseEntity.ok(permissionChanges);
    }
}
