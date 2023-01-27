package com.pjatkInz.logReviewer.repository;

import com.pjatkInz.logReviewer.model.PermissionsChange;
import com.pjatkInz.logReviewer.model.PermissionsRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PermissionsRequestRepository extends JpaRepository<PermissionsRequest, UUID>  {
    List<PermissionsRequest> findAll();

    Iterable<PermissionsRequest> findPermissionsRequestByApplicationId(UUID fromString);
   //Iterable<PermissionsRequest> findPermissionsRequestByApplicationIdAndReconciliationIsNull(UUID fromString);
}
