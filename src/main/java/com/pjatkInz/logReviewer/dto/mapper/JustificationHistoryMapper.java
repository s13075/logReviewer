package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.JustificationHistoryDto;
import com.pjatkInz.logReviewer.model.JustificationHistory;
import org.mapstruct.Mapper;

@Mapper
public interface JustificationHistoryMapper {
    JustificationHistory JustificationHistoryDtoToJustificationHistory(JustificationHistoryDto justificationHistoryDto);
    JustificationHistoryDto JustificationHistoryToJustificationHistoryDto(JustificationHistory justificationHistory);
}
