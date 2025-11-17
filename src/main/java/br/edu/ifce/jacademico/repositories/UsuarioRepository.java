package br.edu.ifce.jacademico.repositories;

// UsuarioRepository: persistence layer for Usuario entity.
import br.edu.ifce.jacademico.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
