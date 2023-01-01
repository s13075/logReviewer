package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.dto.mapper.ApplicationMapper;
import com.pjatkInz.logReviewer.dto.mapper.UserMapper;
import com.pjatkInz.logReviewer.model.Application;
import com.pjatkInz.logReviewer.model.MyUser;
import com.pjatkInz.logReviewer.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final UserMapper userMapper;

    public ApplicationService(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper, UserMapper userMapper){
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
        this.userMapper = userMapper;
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

    private Function<MyUser, UserDto> convertUserToUserDto() {
        return user -> userMapper.userToUserDto(user);
    }


    public List<ApplicationDto> getApplicationsByName(String name) {
        Iterable<Application> applications = applicationRepository.findApplicationsByNameIgnoreCase(name);
        return StreamSupport.stream(applications.spliterator(),false)
                .map(convertApplicationToApplicationDto())
                .collect(Collectors.toList());
    }

    public List<UserDto> getApplicationReviewers(String id){
        Iterable<MyUser> reviewers = applicationRepository.findApplicationById(UUID.fromString(id)).getReviewers();
        return StreamSupport.stream(reviewers.spliterator(),false)
                .map(convertUserToUserDto())
                .collect(Collectors.toList());

    }


}
