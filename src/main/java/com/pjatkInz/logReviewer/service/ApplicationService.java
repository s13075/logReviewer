package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.dto.mapper.ApplicationMapperImpl;
import com.pjatkInz.logReviewer.model.Application;
import com.pjatkInz.logReviewer.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapperImpl applicationMapper;

    public ApplicationService(ApplicationRepository applicationRepository, ApplicationMapperImpl applicationMapper){
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }

    public List<ApplicationDto> getApplications() {
        Iterable<Application> all = applicationRepository.findAll();
        return StreamSupport.stream(all.spliterator(),false)
                .map(application -> applicationMapper.applicationToApplicationDto(application))
                .collect(Collectors.toList());
    }
}
