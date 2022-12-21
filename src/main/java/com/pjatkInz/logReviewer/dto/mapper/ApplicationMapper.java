package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Mapper
public interface ApplicationMapper {
    //ApplicationMapper INSTANCE = Mapper.getMapper(ApplicationMapper.class);

    @Value("${my-api.version}")
    String stringWithDefaultValue = null;


    //@Mapping(target = "id", ignore = true)
    @Mapping(target ="reviewerRefference", source = "id",qualifiedByName = "ReviewerRefference")
    ApplicationDto applicationToApplicationDto(Application application);

    @Named("ReviewerRefference")
    default String defaultValueForQualifier(UUID id) {

        return "http://localhost:8080/api/v1/applications/"+id.toString()+"/reviewers";

    }
}
