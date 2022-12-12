package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.dto.mapper.ApplicationMapper;
import com.pjatkInz.logReviewer.dto.mapper.ApplicationMapperImpl;
import com.pjatkInz.logReviewer.model.Application;
import com.pjatkInz.logReviewer.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public ApplicationService(ApplicationRepository applicationRepository, ApplicationMapperImpl applicationMapper){
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }

    public List<ApplicationDto> getApplications() {
        Iterable<Application> applications = applicationRepository.findAll();
        return StreamSupport.stream(applications.spliterator(),false)
                .map(convertApplicationToApplicationDto())
                .collect(Collectors.toList());
    }


    private Function<Application, ApplicationDto> convertApplicationToApplicationDto() {
        return application -> applicationMapper.applicationToApplicationDto(application);
    }


    public List<ApplicationDto> getApplicationsByName(String name) {
        Iterable<Application> applications = applicationRepository.findApplicationsByNameIgnoreCase(name);
        return StreamSupport.stream(applications.spliterator(),false)
                .map(convertApplicationToApplicationDto())
                .collect(Collectors.toList());
    }
}
