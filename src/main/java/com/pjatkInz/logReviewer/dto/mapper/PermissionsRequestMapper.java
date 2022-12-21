package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.PermissionsRequestDto;
import com.pjatkInz.logReviewer.model.PermissionsChange;
import com.pjatkInz.logReviewer.model.PermissionsRequest;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionsRequestMapper {

    PermissionsRequestDto PermissionsChangeToPermissionsChangeDto(PermissionsRequest permissionsRequest);
    PermissionsRequest permissionsChangeDtoToPermissionsChange(PermissionsRequestDto permissionsRequestDto);
}
