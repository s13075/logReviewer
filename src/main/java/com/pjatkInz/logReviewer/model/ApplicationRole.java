package com.pjatkInz.logReviewer.model;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApplicationRole {

    @Id
    @Column(columnDefinition="uuid")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column
    @NotNull
    @EqualsAndHashCode.Include
    private String roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;
}
