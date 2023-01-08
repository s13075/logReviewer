package com.pjatkInz.logReviewer.repository;

import com.pjatkInz.logReviewer.model.MyRole;
import com.pjatkInz.logReviewer.model.PermissionsChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PermissionsChangeRepository extends JpaRepository<PermissionsChange, UUID>  {
    List<PermissionsChange> findAll();

    List<PermissionsChange> findPermissionsChangeByApplicationId(UUID fromString);

    PermissionsChange findPermissionsChangeById(UUID id);
}
