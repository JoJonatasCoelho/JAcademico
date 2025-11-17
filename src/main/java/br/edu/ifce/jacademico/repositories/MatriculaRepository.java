package br.edu.ifce.jacademico.repositories;

// MatriculaRepository: handles enrollment persistence and duplicate checks.
import br.edu.ifce.jacademico.entities.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    boolean existsByAlunoIdAndDisciplinaId(Long alunoId, Long disciplinaId);

    void deleteAllByAlunoId(Long alunoId);

    void deleteAllByDisciplinaId(Long disciplinaId);
}
