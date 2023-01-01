package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.model.MyUser;
import com.pjatkInz.logReviewer.model.PermissionsChange;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PermissionsChangeMapper {

    PermissionsChange permissionsChangeDtoToPermissionsChange(PermissionsChangeDto permissionsChangeDto);

    @Mapping(source = "application.inventoryNo", target = "applicationInventoryNo")
    @Mapping(source = "application.name", target = "applicationName")
    @Mapping(source = "applicationRole.roleName", target = "applicationRoleName")
    @Mapping(source = "informationSecurityAdministrator.emploeeId", target = "informationSecurityAdministratorEmploeeId")
    @Mapping(source = "subjectUser.emploeeId", target = "subjectUserEmploeeId")
    PermissionsChangeDto PermissionsChangeToPermissionsChangeDto(PermissionsChange permissionsChange);

}
