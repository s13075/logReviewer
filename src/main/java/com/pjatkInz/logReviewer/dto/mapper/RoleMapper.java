package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.RoleDto;
import com.pjatkInz.logReviewer.model.MyRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoleMapper {

    @Mapping(source = "enumRole", target = "roleName")
    RoleDto RoleToRoleDTO(MyRole role);

    @Mapping(source = "roleName", target = "enumRole")
    MyRole RoleDtoToRole(RoleDto roleDto);

}
