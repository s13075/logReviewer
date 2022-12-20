package com.pjatkInz.logReviewer;

import com.pjatkInz.logReviewer.dto.UserDto;
import com.pjatkInz.logReviewer.model.EMyRole;
import com.pjatkInz.logReviewer.model.MyRole;
import com.pjatkInz.logReviewer.model.MyUser;
import com.pjatkInz.logReviewer.repository.RoleRepository;
import com.pjatkInz.logReviewer.repository.UserRepository;
import com.pjatkInz.logReviewer.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class LogReviewerApplication implements CommandLineRunner {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	MyUserDetailsService userDetailsService;

	public static void main(String[] args) {
		SpringApplication.run(LogReviewerApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		List<MyRole> applicationRoles = roleRepository.findAll();
		List<MyUser> applicationUsers = userRepository.findAll();
		Set<MyRole> createdRoles = new HashSet<>();

		if(applicationRoles.size()==0){

			for (EMyRole role : EMyRole.values()) {
				System.out.println(role);
				MyRole createdRole = new MyRole();
				createdRole.setEnumRole(role);
				createdRoles.add(createdRole);
			}
			roleRepository.saveAll(createdRoles);

		}
		if(applicationUsers.size()==0) {

			MyUser rootUser = new MyUser();
			rootUser.setPassword("root123");
			rootUser.setName("root");
			rootUser.setSurname("root");
			rootUser.setEmail("root@root.com");
			rootUser.setEmpoleeId("RT1234");
			rootUser.setRoles(createdRoles);
			userRepository.save(rootUser);
		}
	}
}
