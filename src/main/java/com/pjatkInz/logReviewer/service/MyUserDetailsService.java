package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.dto.mapper.UserMapperImpl;
import com.pjatkInz.logReviewer.model.MyUser;
import com.pjatkInz.logReviewer.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapperImpl userMapper;

    public MyUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapperImpl userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userByMail = getUserByEmail(email);
        return new User(userByMail.getEmail(),userByMail.getPassword(), new ArrayList<>());
    }


    public UUID addUser(UserDto userDto){
        MyUser user = userMapper.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setId(null);

        MyUser createdUser = userRepository.saveAndFlush(user);

        return createdUser.getId();
    }

    public UserDto getUserByEmail(String email){
        MyUser foundUser = userRepository.findByEmail(email);
        if(Objects.isNull(foundUser)){
            throw new RuntimeException("MyUser with email: "+email+" does not exist");
        }

        UserDto returnedUser = userMapper.userToUserDto(foundUser);

        return returnedUser;
    }

}
