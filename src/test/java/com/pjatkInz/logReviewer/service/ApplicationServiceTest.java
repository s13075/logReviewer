package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.dto.mapper.ApplicationMapperImpl;
import com.pjatkInz.logReviewer.model.Application;
import com.pjatkInz.logReviewer.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @InjectMocks
    private ApplicationService applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationMapperImpl applicationMapper;

    @Test
    void shouldReturnListOfApplicationDtoWhenGetApplicationsCalled() {

        List<Application> applications = new ArrayList<>();
        Application application = getApplication();
        applications.add(application);
        ApplicationDto applicationDto = getApplicationDto();
        when(applicationRepository.findAll()).thenReturn(applications);
        when(applicationMapper.applicationToApplicationDto(application)).thenReturn(applicationDto);
        List<ApplicationDto> applicationDtos = applicationService.getApplications();
        assertThat(1).isEqualTo(applicationDtos.size());
        assertThat(applicationDtos.get(0))
                .isNotNull()
                .hasFieldOrPropertyWithValue("inventoryNo","100001")
                .hasFieldOrPropertyWithValue("criticalFunction",true)
                .hasFieldOrPropertyWithValue("piiData",true)
                .hasFieldOrPropertyWithValue("financialOperation",true)
                .hasFieldOrPropertyWithValue("name","Service Test Application")
                .hasFieldOrPropertyWithValue("supportContactGroup","support@testgroup")
                .hasFieldOrPropertyWithValue("smeEmployee","SMEname");
    }

    private Application getApplication(){
        return Application.builder()
                .inventoryNo("100001")
                .criticalFunction(true)
                .piiData(true)
                .financialOperation(true)
                .name("Service Test Application")
                .supportContactGroup("support@testgroup")
                .smeEmployee("SMEname")
                .build();
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
