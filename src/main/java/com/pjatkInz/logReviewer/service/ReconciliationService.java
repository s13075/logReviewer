package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.ReconciliationDto;
import com.pjatkInz.logReviewer.dto.mapper.ReconciliationMapper;
import com.pjatkInz.logReviewer.model.*;
import com.pjatkInz.logReviewer.repository.JustificationRepository;
import com.pjatkInz.logReviewer.repository.PermissionsChangeRepository;
import com.pjatkInz.logReviewer.repository.PermissionsRequestRepository;
import com.pjatkInz.logReviewer.repository.ReconciliationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReconciliationService {

    private final PermissionsChangeRepository permissionsChangeRepository;
    private final PermissionsRequestRepository permissionsRequestRepository;
    private final ReconciliationRepository reconciliationRepository;
    private final ReconciliationMapper reconciliationMapper;
    private final JustificationRepository justificationRepository;

    public ReconciliationService(PermissionsChangeRepository permissionsChangeRepository, PermissionsRequestRepository permissionsRequestRepository, ReconciliationRepository reconciliationRepository, ReconciliationMapper reconciliationMapper, JustificationRepository justificationRepository) {
        this.permissionsChangeRepository = permissionsChangeRepository;
        this.permissionsRequestRepository = permissionsRequestRepository;
        this.reconciliationRepository = reconciliationRepository;
        this.reconciliationMapper = reconciliationMapper;
        this.justificationRepository = justificationRepository;
    }


    public ReconciliationDto reconcilePermissionsChangeWithRequest(ReconciliationDto reconciliationDto){
        Reconciliation reconciliation = new Reconciliation();
        reconciliation.setStatus("REQUESTED");
        reconciliation.setStartDate(LocalDateTime.now());
        reconciliation.setJustification(null);
        Set<PermissionsChange> permisionChanges = new HashSet<>();
        for(PermissionsChangeDto permissionsChangeDto : reconciliationDto.getOfPermisionChanges()){
            PermissionsChange permissionsChange = permissionsChangeRepository.getReferenceById(permissionsChangeDto.getId());
            permisionChanges.add(permissionsChange);
        }
        reconciliation.setOfPermisionChanges(permisionChanges);
        UUID permissionsRequestId = reconciliationDto.getPermissionsRequest().getId();
        PermissionsRequest permissionsRequest = permissionsRequestRepository.getReferenceById(permissionsRequestId);
        reconciliation.setPermissionsRequest(permissionsRequest);
        reconciliationRepository.saveAndFlush(reconciliation);
        return convertReconciliationToReconciliationDto().apply(reconciliation);

    }

    public List<ReconciliationDto> getAllReconciliations(){
        Iterable<Reconciliation> reconciliations = reconciliationRepository.findAll();
        return StreamSupport.stream(reconciliations.spliterator(), false)
                .map(convertReconciliationToReconciliationDto())
                .collect(Collectors.toList());
    }

    @Transactional
    public ReconciliationDto reconcilePermissionsChangeWithJustification(ReconciliationDto reconciliationDto) throws Exception {
        Reconciliation reconciliation = new Reconciliation();
        reconciliation.setStatus("JUSTIFIFICATION REQUIRED");
        reconciliation.setStartDate(LocalDateTime.now());
        reconciliation.setPermissionsRequest(null);

        Set<PermissionsChange> permisionChanges = new HashSet<>();
        MyUser adminUser = null;
        for(PermissionsChangeDto permissionsChangeDto : reconciliationDto.getOfPermisionChanges()){
            PermissionsChange permissionsChange = permissionsChangeRepository.getReferenceById(permissionsChangeDto.getId());
            if(adminUser == null){
                adminUser = permissionsChange.getInformationSecurityAdministrator();
            }else{
                if(adminUser == permissionsChange.getInformationSecurityAdministrator()){
                    permisionChanges.add(permissionsChange);
                }else{
                        throw new Exception("VARIOUS ADMIN VALUES ASSIGNED - NOT ALLOWED");
                }
            }
        }
        MyUser creatingUser = getUserCreateingReconciliation().get();
        reconciliation.setOfPermisionChanges(permisionChanges);
        Justification justification = new Justification();
        justification.setCurentStatus("PENDING ADMIN JUSTIFICATION");
        justification.setReconciliation(reconciliation);
        justification.setAssignedReviewer(creatingUser);
        justification.setAssignedISA(adminUser);
        reconciliation.setJustification(justification);
        justificationRepository.save(justification);
        reconciliationRepository.saveAndFlush(reconciliation);
        return convertReconciliationToReconciliationDto().apply(reconciliation);
    }

    private Function<Reconciliation, ReconciliationDto> convertReconciliationToReconciliationDto() {
        return reconciliation -> reconciliationMapper.reconciliationToReconciliationDto(reconciliation);
    }

    private Optional<MyUser> getUserCreateingReconciliation(){

        Optional<MyUser> myUser = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(MyUserDetails.class::cast)
                .map(MyUserDetails::getUser);
        return myUser;
    }


}
