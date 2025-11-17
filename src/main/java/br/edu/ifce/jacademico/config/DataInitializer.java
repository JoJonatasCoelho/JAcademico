package br.edu.ifce.jacademico.config;

import br.edu.ifce.jacademico.entities.Role;
import br.edu.ifce.jacademico.entities.Usuario;
import br.edu.ifce.jacademico.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UsuarioRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (!repository.findByUsername("admin").isPresent()) {
                repository.save(new Usuario("admin", encoder.encode("admin123"), Role.ROLE_ADMIN));
            }
            if (!repository.findByUsername("secretaria").isPresent()) {
                repository.save(new Usuario("secretaria", encoder.encode("secret123"), Role.ROLE_SECRETARIA));
            }
        };
    }
}
