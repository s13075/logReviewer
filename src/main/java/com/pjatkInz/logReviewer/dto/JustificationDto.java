package com.pjatkInz.logReviewer.dto;

import com.pjatkInz.logReviewer.dto.mapper.JustificationHistoryMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JustificationDto {

    private UUID id;
    private String curentStatus;
    private String lastComment;
    private String assignedISAEmploeeId;
    private String assignedReviewerEmploeeId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Set<JustificationHistoryDto> justificationHistorySet;

}
