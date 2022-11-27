package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ApplicationMapper {
    //ApplicationMapper INSTANCE = Mapper.getMapper(ApplicationMapper.class);

    @Mapping(target = "id", ignore = true)
    ApplicationDto applicationToApplicationDto(Application application);
}
