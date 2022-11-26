package com.pjatkInz.logReviewer.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    private UUID id;

    @Column
    @NotNull
    private String inventoryNo;
    @Column
    @NotNull
    private String name;
    @Column
    @NotNull
    private Boolean piiData;
    @Column
    @NotNull
    private Boolean criticalFunction;
    @Column
    @NotNull
    private Boolean financialOperation;
    @Column
    @NotNull
    private String supportContactGroup;

    @Column
    @NotNull
    private String smeEmployee;

}
