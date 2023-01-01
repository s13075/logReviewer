package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.PermissionsRequestDto;
import com.pjatkInz.logReviewer.model.PermissionsChange;
import com.pjatkInz.logReviewer.model.PermissionsRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PermissionsRequestMapper {

    @Mapping(source = "application.inventoryNo", target = "applicationInventoryNo")
    @Mapping(source = "application.name", target = "applicationName")
    @Mapping(source = "applicationRole.roleName", target = "applicationRoleName")
    @Mapping(source = "informationSecurityAdministrator.emploeeId", target = "informationSecurityAdministratorEmploeeId")
    @Mapping(source = "subjectUser.emploeeId", target = "subjectUserEmploeeId")
    @Mapping(source = "approverUser.emploeeId", target = "approverUserEmploeeId")
    PermissionsRequestDto PermissionsRequestToPermissionsRequestDto(PermissionsRequest permissionsRequest);


    PermissionsRequest permissionsRequestDtoToPermissionsRequest(PermissionsRequestDto permissionsRequestDto);
}
