package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.model.MyUser;
import com.pjatkInz.logReviewer.model.PermissionsChange;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionsChangeMapper {

    PermissionsChange permissionsChangeDtoToPermissionsChange(PermissionsChangeDto permissionsChangeDto);
    PermissionsChangeDto PermissionsChangeToPermissionsChangeDto(PermissionsChange permissionsChange);

}
