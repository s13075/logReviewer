package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.model.MyRole;
import com.pjatkInz.logReviewer.model.MyUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {


    UserDto userToUserDto(MyUser user);
    MyUser userDtoToUser(UserDto userDto);


}
