package br.edu.ifce.jacademico.repositories;

// AlunoRepository: CRUD operations for students with finder helpers.
import br.edu.ifce.jacademico.entities.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByMatricula(String matricula);
}
