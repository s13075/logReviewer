package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.dto.ReconciliationDto;
import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.service.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${my-api.version}"+"${my-api.reconciliation}")
public class ReconciliationController {

    @Autowired
    private ReconciliationService reconciliationService;

    @PostMapping("/requested")
    @PreAuthorize("hasRole('REVIEWER')")
    public ResponseEntity<ReconciliationDto> reconcileWithRequest(@Valid @RequestBody ReconciliationDto reconciliationDto){
        ReconciliationDto createdReconciliation = reconciliationService.reconcilePermissionsChangeWithRequest(reconciliationDto);
        return ResponseEntity.ok(createdReconciliation);
    }

    @GetMapping
    @PreAuthorize("hasRole('REVIEWER')")
    public ResponseEntity<List<ReconciliationDto>> listReconciliations(){
        List<ReconciliationDto> reconciliations = reconciliationService.getAllReconciliations();
        return ResponseEntity.ok(reconciliations);
    }
    @PostMapping("/justified")
    @PreAuthorize("hasRole('REVIEWER')")
    public ResponseEntity<ReconciliationDto> reconcileWithJustification(@Valid @RequestBody ReconciliationDto reconciliationDto){
        ReconciliationDto createdReconciliation = null;
        try {
            createdReconciliation = reconciliationService.reconcilePermissionsChangeWithJustification(reconciliationDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(createdReconciliation);
    }
}
