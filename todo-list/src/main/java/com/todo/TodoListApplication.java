package com.todo;

import com.todo.model.Role;
import com.todo.model.Usuario;
import com.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TodoListApplication implements CommandLineRunner {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;



	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.findByName("admin").isEmpty()) {
			Usuario admin = new Usuario();
			admin.setName("admin");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setRole(Role.ADMIN);
			userRepository.save(admin);
			System.out.println("Usuário admin criado!");
		} else {
			System.out.println("Usuário admin já existe.");
		}
	}

}
