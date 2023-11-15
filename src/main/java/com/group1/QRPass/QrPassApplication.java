package com.group1.QRPass;

import com.group1.QRPass.model.Role;
import com.group1.QRPass.model.User;
import com.group1.QRPass.repository.RoleRepository;
import com.group1.QRPass.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class QrPassApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrPassApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, RoleRepository roleRepository,
										PasswordEncoder passwordEncoder){
		return args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
			roleRepository.save(new Role(1L,"ADMIN"));
		};
	}
}
