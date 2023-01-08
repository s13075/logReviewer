package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.JustificationDto;
import com.pjatkInz.logReviewer.model.Justification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (uses = {JustificationHistoryMapper.class})
public interface JustificationMapper {

    Justification justificationDtoToJustification(JustificationDto justificationDot);

    @Mapping(source = "assignedISA.emploeeId", target = "assignedISAEmploeeId")
    @Mapping(source = "assignedReviewer.emploeeId", target = "assignedReviewerEmploeeId")
    JustificationDto justificationToJustificationDto(Justification justification);

}
