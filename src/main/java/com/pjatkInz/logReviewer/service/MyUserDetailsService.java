package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.dto.mapper.UserMapper;
import com.pjatkInz.logReviewer.model.*;
import com.pjatkInz.logReviewer.repository.RoleRepository;
import com.pjatkInz.logReviewer.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public MyUserDetailsService(UserRepository userRepository, RoleRepository roleRepository,  PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            MyUser user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("MyUser with email: "+email+" does not exist");
            }
            return new MyUserDetails(user);
    }


    public UUID addUser(UserDto userDto){

        MyUser user = userMapper.userDtoToUser(userDto);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setId(null);
        user.setEnabled(true);

        MyRole role = roleRepository.findByEnumRole(EMyRole.REVIEWER);
        MyRole role2 = roleRepository.findByEnumRole(EMyRole.REVIEWER_MANAGER);
        Set<MyRole> roles = new HashSet<>();
        roles.add(role);
        roles.add(role2);
        user.setRoles(roles);

        MyUser createdUser = userRepository.saveAndFlush(user);
        return createdUser.getId();
    }
    public UUID addUserWithRoles(UserDto userDto, Set<MyRole> roles){

        MyUser user = userMapper.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setId(null);
        user.setEnabled(true);

        Set<MyRole> attached = new HashSet<>();
        for(MyRole role : roles){
            MyRole atachedRole = roleRepository.findByEnumRole(role.getEnumRole());
            attached.add(atachedRole);
        }

        user.setRoles(attached);

        MyUser createdUser = userRepository.saveAndFlush(user);
        return createdUser.getId();
    }


    @Transactional
    public UserDto getUserByEmail(String email){
        MyUser foundUser = userRepository.findByEmail(email);
        if(Objects.isNull(foundUser)){
            throw new RuntimeException("MyUser with email: "+email+" does not exist");
        }

        UserDto returnedUser = userMapper.userToUserDto(foundUser);

        return returnedUser;
    }

}
