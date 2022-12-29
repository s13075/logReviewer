package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.ApplicationDto;
import com.pjatkInz.logReviewer.dto.RoleDto;
import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.dto.mapper.UserMapper;
import com.pjatkInz.logReviewer.model.*;
import com.pjatkInz.logReviewer.repository.RoleRepository;
import com.pjatkInz.logReviewer.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public MyUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            MyUser user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("MyUser with email: "+email+" does not exist");
            }
            return new MyUserDetails(user);
    }


    public UserDto addUser(UserDto userDto){

        MyUser user = userMapper.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setId(null);
        user.setEnabled(true);
        user.setEmploeeId(generateEmploeeId(userDto));

        Set<MyRole> roles = new HashSet<>();
        Set<RoleDto> dtoRoles = userDto.getRoles();
        for(RoleDto roleDto : dtoRoles){
            MyRole atachedRole = roleRepository.findByEnumRole(EMyRole.valueOf(roleDto.getRoleName()));
            roles.add(atachedRole);
        }
        user.setRoles(roles);

        MyUser createdUser = userRepository.saveAndFlush(user);
        createdUser.setPassword("***");
        return convertUserToUserDto().apply(createdUser);
    }
    public UserDto addUserWithRoles(UserDto userDto, Set<MyRole> roles){

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
        createdUser.setPassword("***");

        return convertUserToUserDto().apply(createdUser);
    }

    @Transactional
    public UserDto getUserByEmail(String email){
        MyUser foundUser = userRepository.findByEmail(email);
        if(Objects.isNull(foundUser)) {
            throw new RuntimeException("MyUser with email: " + email + " does not exist");
        }
        return convertUserToUserDto().apply(foundUser);
    }

    public List<UserDto> getUsers(){
        List<MyUser> users = userRepository.findAll();

        return StreamSupport.stream(users.spliterator(),false)
                .map(convertUserToUserDto())
                .peek(user -> user.setPassword(("***")))
                .collect(Collectors.toList());
    }

    public UserDto getUserByEmploeeId(String emploeeId){
        MyUser user = userRepository.findByEmploeeId(emploeeId);
        if(Objects.isNull(user)) {
            throw new RuntimeException("MyUser with id: " + emploeeId + " does not exist");
        }
        UserDto userDto = convertUserToUserDto().apply(user);
        userDto.setPassword("***");
        return userDto;

    }

    public Function<MyUser, UserDto> convertUserToUserDto() {
        return user -> userMapper.userToUserDto(user);
    }


    public UserDto updateUser(String emploeeId, UserDto userDto) {
        MyUser user = userRepository.findByEmploeeId(emploeeId);
        if(Objects.isNull(user)) {
            throw new RuntimeException("MyUser with id: " + emploeeId + " does not exist");
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        Set<MyRole> roles = new HashSet<>();
        Set<RoleDto> dtoRoles = userDto.getRoles();
        for(RoleDto roleDto : dtoRoles){
            MyRole atachedRole = roleRepository.findByEnumRole(EMyRole.valueOf(roleDto.getRoleName()));
            roles.add(atachedRole);
        }
        user.setRoles(roles);

        MyUser updatedUser = userRepository.saveAndFlush(user);
        updatedUser.setPassword("***");
        return convertUserToUserDto().apply(updatedUser);
    }
    private String generateEmploeeId(UserDto userDto){

        String emploeeIdStartLetters = userDto.getName().substring(0, 1) + userDto.getSurname().substring(0, 1);
        List<MyUser> usersStartingLikeThis = userRepository.findByEmploeeIdStartsWith(emploeeIdStartLetters);
        Integer userNumber = usersStartingLikeThis.size() + 1;
        String emploeeId = emploeeIdStartLetters + StringUtils.leftPad(userNumber.toString(), 4, "0");

        return emploeeId;

    }
}
