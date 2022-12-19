package com.pjatkInz.logReviewer.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Justification {

    @Id
    @Column(columnDefinition="uuid")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    private UUID id;

    @Column
    @NotNull
    private String status;

    @OneToOne
    private Reconciliation reconciliation;

    @OneToMany
    private Set<Escalation> escalations;

}
