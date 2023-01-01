package com.pjatkInz.logReviewer.dto;

import com.pjatkInz.logReviewer.model.Application;
import com.pjatkInz.logReviewer.model.ApplicationRole;
import com.pjatkInz.logReviewer.model.InformationSecurityAdministrator;
import com.pjatkInz.logReviewer.model.MyUser;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionsRequestDto {

    private UUID id;
    private LocalDateTime eventDate;
    private String informationSecurityAdministratorEmploeeId;
    private String applicationName;
    private String applicationInventoryNo;
    private String applicationRoleName;
    private String subjectUserEmploeeId;
    private String requestNumber;
    private String status;
    private String approverUserEmploeeId;

}
