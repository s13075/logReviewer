package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.model.MyUser;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto userToUserDto(MyUser user);
    MyUser userDtoToUser(UserDto userDto);
}
