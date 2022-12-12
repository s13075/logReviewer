package com.pjatkInz.logReviewer.repository;

import com.pjatkInz.logReviewer.model.Application;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends CrudRepository<Application, UUID> {
    List<Application> findAll();
    List<Application> findApplicationsByName(String name);
    List<Application> findApplicationsByNameIgnoreCase(String name);
}
