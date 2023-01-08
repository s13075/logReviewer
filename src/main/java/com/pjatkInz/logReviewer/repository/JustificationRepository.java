package com.pjatkInz.logReviewer.repository;

import com.pjatkInz.logReviewer.model.Justification;
import com.pjatkInz.logReviewer.model.PermissionsChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JustificationRepository extends JpaRepository<Justification, UUID> {
    List<Justification> findAll();

}
