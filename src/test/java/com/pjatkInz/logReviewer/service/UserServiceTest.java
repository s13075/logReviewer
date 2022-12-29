package com.pjatkInz.logReviewer.service;


import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.dto.mapper.UserMapperImpl;
import com.pjatkInz.logReviewer.model.MyUser;
import com.pjatkInz.logReviewer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapperImpl userMapper;

    @Test
    void shouldReturnUserIdWhenCalledWithUserData() {
        UUID id = UUID.randomUUID();
        when(userRepository.saveAndFlush(any())).thenReturn(getUser(id));
        when(userMapper.userDtoToUser(any())).thenReturn(getUser(id));
        //when(userMapper.userToUserDto(any())).thenReturn(getUserDto());
        UserDto uuid = myUserDetailsService.addUser(getUserDto());
        assertThat(uuid).isNotNull();
        assertThat(uuid).isEqualTo(id);

    }
    @Test
    void shouldReturnUserDtoWhenCalledWithUserEmail() {

        when(userRepository.findByEmail(any())).thenReturn(getUserByMail());
        when(userMapper.userToUserDto(any())).thenReturn(getUserDto());

        UserDto userDto = myUserDetailsService.getUserByEmail("testUser@emial.com");

        assertThat(userDto.getId()).isNotNull();
        assertThat(userDto.getId()).isEqualTo(UUID.fromString("123e4567-e89b-42d3-a456-556642440004"));

    }
    @Test
    void shouldThrowErrorWhenCalledWithUnknownUserEmail() {

        when(userRepository.findByEmail(any())).thenThrow(new RuntimeException("error"));
        assertThatThrownBy(() -> myUserDetailsService.getUserByEmail("testUser@emial.com")).isInstanceOf(RuntimeException.class);

    }

    private MyUser getUser(UUID id){
        return MyUser.builder()
                .id(id)
                .name("testUser")
                .email("testUser@emial.com")
                .password("testUserPassword")
                .build();
    }
    private UserDto getUserDto(){
        return UserDto.builder()
                .id(UUID.fromString("123e4567-e89b-42d3-a456-556642440004"))
                .name("testUser")
                .email("testUser@emial.com")
                .password("testUserPassword")
                .build();
    }
    private MyUser getUserByMail(){
        return MyUser.builder()
                .id(UUID.fromString("123e4567-e89b-42d3-a456-556642440004"))
                .name("testUser")
                .email("testUser@emial.com")
                .password("testUserPassword")
                .build();
    }


}