package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.JustificationDto;
import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.mapper.JustificationMapper;
import com.pjatkInz.logReviewer.model.Justification;
import com.pjatkInz.logReviewer.model.JustificationHistory;
import com.pjatkInz.logReviewer.model.MyUserDetails;
import com.pjatkInz.logReviewer.model.PermissionsChange;
import com.pjatkInz.logReviewer.repository.JustificationHistoryRepository;
import com.pjatkInz.logReviewer.repository.JustificationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JustificationService {

    private final JustificationRepository justificationRepository;
    private final JustificationHistoryRepository justificationHistoryRepository;
    private final JustificationMapper justificationMapper;
    private final PermissionsChangeService permissionsChangeService;

    public JustificationService(JustificationRepository justificationRepository, JustificationHistoryRepository justificationHistoryRepository, JustificationMapper justificationMapper, PermissionsChangeService permissionsChangeService) {
        this.justificationRepository = justificationRepository;
        this.justificationHistoryRepository = justificationHistoryRepository;
        this.justificationMapper = justificationMapper;
        this.permissionsChangeService = permissionsChangeService;
    }


    public JustificationDto updateJustification(UUID justificationId, JustificationDto justificationDto) {
        Justification justification = justificationRepository.getReferenceById(justificationId);

        JustificationHistory justificationHistory = new JustificationHistory();
        justificationHistory.setOldStatus(justification.getCurentStatus());
        justificationHistory.setNewStatus(justificationDto.getCurentStatus());
        justificationHistory.setPreviousComment(justification.getLastComment());
        justificationHistory.setNewComment(justificationDto.getLastComment());
        justificationHistory.setJustification(justification);
        justificationHistoryRepository.save(justificationHistory);

        justification.setCurentStatus(justificationDto.getCurentStatus());
        justification.setLastComment(justificationDto.getLastComment());
        justification.getJustificationHistorySet().add(justificationHistory);
        justificationRepository.saveAndFlush(justification);
        return convertJustificationToJustificationDto().apply(justification);
    }

    private Function<Justification, JustificationDto> convertJustificationToJustificationDto() {
        return justification -> justificationMapper.justificationToJustificationDto(justification);
    }

    public List<JustificationDto> getJustifications() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("REVIEWER_MANAGER"))) {
            return getAllJustifications();
        }
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("REVIEWER"))) {
            return getReviewerJustifications(userDetails);
        }
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("REVIEWED_ISA"))) {
            return getReviewedAdminJustifications(userDetails);
        }
        return null;
    }

    @PreAuthorize("hasRole('REVIEWER_MANAGER')")
    public List<JustificationDto> getAllJustifications() {
        List<Justification> justifications =  justificationRepository.findAll();
        return StreamSupport.stream(justifications.spliterator(),false)
                .map(convertJustificationToJustificationDto())
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('REVIEWER')")
    public List<JustificationDto> getReviewerJustifications(MyUserDetails userDetails) {
        List<Justification> justifications =  justificationRepository.findByAssignedReviewerId(userDetails.getUser().getId());
        return StreamSupport.stream(justifications.spliterator(),false)
                .map(convertJustificationToJustificationDto())
                .collect(Collectors.toList());

    }

    @PreAuthorize("hasRole('REVIEWED_ISA')")
    public List<JustificationDto> getReviewedAdminJustifications(MyUserDetails userDetails) {
        List<Justification> justifications =  justificationRepository.findByAssignedISAId(userDetails.getUser().getId());
        return StreamSupport.stream(justifications.spliterator(),false)
                .map(convertJustificationToJustificationDto())
                .collect(Collectors.toList());
    }

    public List<PermissionsChangeDto> getPermissionChangesByJustificationId(UUID justificationId) {
        Justification justification = justificationRepository.getReferenceById(justificationId);
        Set<PermissionsChange> permissionsChangeSet = justification.getReconciliation().getOfPermisionChanges();

        return StreamSupport.stream(permissionsChangeSet.spliterator(),false)
                .map(permissionsChangeService.convertPermissionsChangeToPermissionsChangeDto())
                .collect(Collectors.toList());
    }


}
