package com.pjatkInz.logReviewer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
@EntityListeners(AuditingEntityListener.class)
public class Justification {

    @Id
    @Column(columnDefinition="uuid")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    private UUID id;

    @Column
    @NotNull
    private String curentStatus;

    @Column
    private String lastComment;

    @CreatedDate
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime modifiedDate;

    @ManyToOne
    private MyUser assignedISA;

    @ManyToOne
    private MyUser assignedReviewer;

    @OneToOne
    private Reconciliation reconciliation;

    @OneToMany
    @EqualsAndHashCode.Exclude
    private Set<Escalation> escalations;

    @OneToMany
    @JoinColumn(name = "JUSTIFICATION_ID")
    @NotNull
    @EqualsAndHashCode.Exclude
    private Set<JustificationHistory> justificationHistorySet;



}
