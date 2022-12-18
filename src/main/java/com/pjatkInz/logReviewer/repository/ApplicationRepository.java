package com.pjatkInz.logReviewer.repository;

import com.pjatkInz.logReviewer.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findAll();
    List<Application> findApplicationsByName(String name);
    List<Application> findApplicationsByNameIgnoreCase(String name);
}
