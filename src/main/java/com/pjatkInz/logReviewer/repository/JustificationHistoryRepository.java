package com.pjatkInz.logReviewer.repository;

import com.pjatkInz.logReviewer.model.JustificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JustificationHistoryRepository extends JpaRepository<JustificationHistory, UUID> {
}
