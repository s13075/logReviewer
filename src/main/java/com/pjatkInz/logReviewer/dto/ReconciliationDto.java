package com.pjatkInz.logReviewer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pjatkInz.logReviewer.model.Justification;
import com.pjatkInz.logReviewer.model.PermissionsChange;
import com.pjatkInz.logReviewer.model.PermissionsRequest;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReconciliationDto {

    private UUID id;
    private String status;
    private LocalDateTime startDate;
    private Set<PermissionsChangeDto> ofPermisionChanges;
    private PermissionsRequestDto permissionsRequest;
    private JustificationDto justification;
}
