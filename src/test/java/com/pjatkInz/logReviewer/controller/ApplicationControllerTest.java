package com.pjatkInz.logReviewer.controller;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {

    @InjectMocks
    private ApplicationController applicationController;

    @Mock
    private ApplicationService applicationService;

    @Test
    void shouldReturnApplicationDtoListWhenGetApplicationsCalled(){
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        applicationDtos.add(getApplicationDto());
        when(applicationService.getApplications()).thenReturn(applicationDtos);
        ResponseEntity<List<ApplicationDto>> applications = applicationController.getApplications();
        assertThat(applications.getBody()).isNotNull();
        assertThat(applications.getBody().size()).isEqualTo(1);
    }

    @Test
    void shouldReturnApplicationDtoListWhenGetApplicationsByNameCalled(){
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        applicationDtos.add(getApplicationDto());
        when(applicationService.getApplicationsByName(anyString())).thenReturn(applicationDtos);
        ResponseEntity<List<ApplicationDto>> applications = applicationController.getApplicationsByName("service test application");
        assertThat(applications.getBody()).isNotNull();
        assertThat(applications.getBody().size()).isEqualTo(1);
    }

    private ApplicationDto getApplicationDto(){
        return ApplicationDto.builder()
                .inventoryNo("100001")
                .criticalFunction(true)
                .piiData(true)
                .financialOperation(true)
                .name("Service Test Application")
                .supportContactGroup("support@testgroup")
                .smeEmployee("SMEname")
                .build();
    }

}
