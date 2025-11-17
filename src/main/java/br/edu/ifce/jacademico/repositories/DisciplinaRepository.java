package br.edu.ifce.jacademico.repositories;

// DisciplinaRepository: persistence gateway for course records.
import br.edu.ifce.jacademico.entities.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    Optional<Disciplina> findByCodigo(String codigo);
}
