package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.dto.mapper.ApplicationMapper;
import com.pjatkInz.logReviewer.dto.mapper.UserMapper;
import com.pjatkInz.logReviewer.model.Application;
import com.pjatkInz.logReviewer.model.MyUser;
import com.pjatkInz.logReviewer.model.MyUserDetails;
import com.pjatkInz.logReviewer.repository.ApplicationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public ApplicationService(ApplicationRepository applicationRepository,
                              ApplicationMapper applicationMapper,
                              UserMapper userMapper){
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
        this.userMapper = userMapper;
    }

    public List<ApplicationDto> getApplications() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("REVIEWER_MANAGER"))) {
            return getAllApplications();
        }
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("REVIEWER"))) {
            return getReviewerApplications();
        }
        return null;
    }


    @PreAuthorize("hasRole('REVIEWER_MANAGER')")
    private List<ApplicationDto> getAllApplications(){
        Iterable<Application> applications = null;

        applications = applicationRepository.findAll();
        return StreamSupport.stream(applications.spliterator(),false)
                .map(convertApplicationToApplicationDto())
                .collect(Collectors.toList());

    }
    @PreAuthorize("hasRole('REVIEWER')")
    private List<ApplicationDto> getReviewerApplications() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Application> applications = applicationRepository.findApplicationsByReviewersId(userDetails.getUser().getId());
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("REVIEWER_MANAGER"))) {
            return getAllApplicationsByName(name);
        }
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("REVIEWER"))) {
            return getReviewerApplicationsByName(name);
        }
        return null;
    }

    @PreAuthorize("hasRole('REVIEWER_MANAGER')")
    public List<ApplicationDto> getAllApplicationsByName(String name) {
        Iterable<Application> applications = applicationRepository.findApplicationsByNameStartsWithIgnoreCase(name);
        return StreamSupport.stream(applications.spliterator(),false)
                .map(convertApplicationToApplicationDto())
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('REVIEWER')")
    public List<ApplicationDto> getReviewerApplicationsByName(String title) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Application> applications = applicationRepository.findApplicationsByReviewersIdAndNameStartsWithIgnoreCase(userDetails.getUser().getId(), title);
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
