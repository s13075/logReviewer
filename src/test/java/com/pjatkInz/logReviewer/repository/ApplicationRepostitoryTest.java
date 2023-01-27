package com.pjatkInz.logReviewer.repository;

import com.pjatkInz.logReviewer.model.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.StreamSupport;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ApplicationRepostitoryTest {

    @Autowired
    private ApplicationRepository applicationRepostitory;

    @Test
    @Sql(scripts={"classpath:insertInitialApplicationRecordsForTest.sql"})
    void shouldAbleToFetchAllApplicationsInDB(){
        Iterable<Application> all = applicationRepostitory.findAll();
        long totalAppCount = StreamSupport.stream(all.spliterator(), false).count();
        Assertions.assertEquals(totalAppCount,4);
    }

    @Test
    @Sql(scripts={"classpath:insertInitialApplicationRecordsForTest.sql"})
    void shouldReturnOneApplicationWhenTitleIsTestApplication(){
        List<Application> testApplication = applicationRepostitory.findApplicationsByName("TestApplication");
        Assertions.assertEquals(testApplication.size(),1);
    }

    @Test
    @Sql(scripts={"classpath:insertInitialApplicationRecordsForTest.sql"})
    void shouldReturnOneApplicationWhenTitleIsTestApplicationIgnoreCase(){
        List<Application> testApplication = applicationRepostitory.findApplicationsByNameStartsWithIgnoreCase("testapplication");
        Assertions.assertEquals(testApplication.size(),1);
    }
}
