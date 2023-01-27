package com.pjatkInz.logReviewer.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class PermissionsChange extends InformationSecurityAdministrationEvent{

    @Column
    @NotNull
    private String additionalDetails;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECONCILIATION_ID", insertable = false, updatable = false)
    private Reconciliation reconciliation;

}
