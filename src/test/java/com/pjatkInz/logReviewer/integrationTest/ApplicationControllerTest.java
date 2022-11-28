package com.pjatkInz.logReviewer.integrationTest;


import com.pjatkInz.logReviewer.LogReviewerApplication;
import com.pjatkInz.logReviewer.dto.ApplicationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = LogReviewerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// czysci kontext po każdym tescie - inaczej sql script nie moze ponownie dodac obiektu o tym samym id
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApplicationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql(scripts = {"classpath:insertInitialApplicationRecordsForTest.sql"})
    void shouldReturnApplicationsWhenApplicationsApiCalled(){
        ApplicationDto[] listOfApplications = testRestTemplate.getForObject("http://localhost:"+port+"/api/v1/applications", ApplicationDto[].class);
        assertThat(listOfApplications).isNotNull();
        assertThat(listOfApplications.length).isEqualTo(4);
    }


}
