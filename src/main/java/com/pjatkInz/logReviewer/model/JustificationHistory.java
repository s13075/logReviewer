package com.pjatkInz.logReviewer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class JustificationHistory {

    @Id
    @Column(columnDefinition="uuid")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @EqualsAndHashCode.Include
    private UUID id;

    @CreatedDate
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdDate;

    @Column(name = "CHANGED_BY")
    @CreatedBy
    @EqualsAndHashCode.Include
    private String changedBy;

    @Column
    @NotNull
    private String newStatus;

    @Column
    @NotNull
    private String oldStatus;

    @Column
    @NotNull
    private String previousComment;

    @Column
    @NotNull
    private String newComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JUSTIFICATION_ID", insertable = false, updatable = false)
    private Justification justification;


}
