package com.pjatkInz.logReviewer.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JustificationHistoryDto {

    private UUID id;
    private LocalDateTime createdDate;
    private String changedBy;
    private String newStatus;
    private String oldStatus;
    private String previousComment;
    private String newComment;
}
