package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.dto.mapper.UserMapperImpl;
import com.pjatkInz.logReviewer.model.EMyRole;
import com.pjatkInz.logReviewer.model.MyRole;
import com.pjatkInz.logReviewer.model.MyUser;
import com.pjatkInz.logReviewer.model.MyUserDetails;
import com.pjatkInz.logReviewer.repository.RoleRepository;
import com.pjatkInz.logReviewer.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapperImpl userMapper;

    public MyUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapperImpl userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }


    @Override
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
        Set<MyRole> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

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
