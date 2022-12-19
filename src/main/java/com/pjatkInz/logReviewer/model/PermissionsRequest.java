package com.pjatkInz.logReviewer.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionsRequest extends InformationSecurityAdministrationEvent{

    @Column
    @NotNull
    private String additionalDetails;
}
