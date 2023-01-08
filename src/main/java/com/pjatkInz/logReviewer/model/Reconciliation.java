package com.pjatkInz.logReviewer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class Reconciliation {

    @Id
    @Column(columnDefinition="uuid")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column
    @NotNull
    @EqualsAndHashCode.Include
    private String status;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @NotNull
    @EqualsAndHashCode.Include
    private LocalDateTime startDate;

    @OneToMany
    @JoinColumn(name = "RECONCILIATION_ID")
    @NotNull
    private Set<PermissionsChange> ofPermisionChanges;

    @ManyToOne
    private PermissionsRequest permissionsRequest;

    @OneToOne
    private Justification justification;

    @Column(name = "CREATED_BY")
    @CreatedBy
    private String createdBy;

}
