package com.pjatkInz.logReviewer.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {
    private UUID id;
    private String name;
    private String inventoryNo;
    private Boolean piiData;
    private Boolean criticalFunction;
    private Boolean financialOperation;
    private String supportContactGroup;
    private String smeEmployee;
}
