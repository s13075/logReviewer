package com.pjatkInz.logReviewer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Escalation {

    @Id
    @Column(columnDefinition="uuid")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    private UUID id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @NotNull
    private LocalDateTime startDate;

    @Column
    @NotNull
    private String level;

    @ManyToOne
    @NotNull
    private Justification justification;

    @ManyToOne
    @NotNull
    private InformationSecurityAdministrator informationSecurityAdministrator;

    @ManyToOne
    private MyUser informationSecurityAdministratorManager;

    @ManyToOne
    private MyUser informationSecurityAdministrator2ndLevelManager;

    @ManyToOne
    private MyUser inforrmationSecurityOficer;

    @ManyToOne
    private MyUser complianceOficer;

}
