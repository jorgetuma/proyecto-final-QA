package com.jorge.aneury.proyecto_final_QA;

import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import com.jorge.aneury.proyecto_final_QA.repositorios.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ProyectoFinalQaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalQaApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
		System.out.println("Inicializando los datos.");
		return args -> {

			//Cargando la información.
			Usuario admin = new Usuario();
			admin.setUserName("admin");
			admin.setPassword(passwordEncoder.encode("admin"));
			admin.setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_USER", "ROLE_INVITADO"));
			usuarioRepository.save(admin);

			Usuario user = new Usuario();
			user.setUserName("user");
			user.setPassword(passwordEncoder.encode("user"));
			user.setRoles(List.of("ROLE_USER"));
			usuarioRepository.save(user);
		};
	}

}
