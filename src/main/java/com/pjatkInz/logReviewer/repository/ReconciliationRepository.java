package com.pjatkInz.logReviewer.repository;

import com.pjatkInz.logReviewer.model.Justification;
import com.pjatkInz.logReviewer.model.Reconciliation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface
ReconciliationRepository extends JpaRepository<Reconciliation, UUID> {

    List<Reconciliation> findAll();
}
