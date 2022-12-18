package com.pjatkInz.logReviewer;

import com.pjatkInz.logReviewer.model.EMyRole;
import com.pjatkInz.logReviewer.model.MyRole;
import com.pjatkInz.logReviewer.repository.RoleRepository;
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

	public static void main(String[] args) {
		SpringApplication.run(LogReviewerApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		List<MyRole> applicationRoles = roleRepository.findAll();
		if(applicationRoles.size()==0){
			Set<MyRole> createdRoles = new HashSet<>();
			for (EMyRole role : EMyRole.values()) {
				System.out.println(role);
				MyRole createdRole = new MyRole();
				createdRole.setEnumRole(role);
				createdRoles.add(createdRole);
			}
			roleRepository.saveAllAndFlush(createdRoles);
		}

	}
}
