package com.pjatkInz.logReviewer;

import com.pjatkInz.logReviewer.configuration.SpringSecurityAuditorAware;
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
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class LogReviewerApplication implements CommandLineRunner {

	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditorAware();
	}

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

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
			rootUser.setPassword(passwordEncoder.encode("root123"));
			rootUser.setName("root");
			rootUser.setSurname("root");
			rootUser.setEmail("root@root.com");
			rootUser.setEmploeeId("RT1234");
			rootUser.setRoles(createdRoles);
			userRepository.save(rootUser);
		}
	}
}
