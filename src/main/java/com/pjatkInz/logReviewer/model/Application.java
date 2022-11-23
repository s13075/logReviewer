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
    private String name;
    @Column
    private Boolean piiData;
    @Column
    private Boolean criticalFunction;
    @Column
    private Boolean financialOperation;
    @Column
    private String supportContactGroup;

    private String smeEmployee;

}
