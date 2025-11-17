package br.edu.ifce.jacademico.repositories;

// MatriculaRepositoryTests: validates duplicate detection helper at persistence level.
import br.edu.ifce.jacademico.entities.Aluno;
import br.edu.ifce.jacademico.entities.AlunoStatus;
import br.edu.ifce.jacademico.entities.Disciplina;
import br.edu.ifce.jacademico.entities.Matricula;
import br.edu.ifce.jacademico.entities.MatriculaSituacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@DataJpaTest
@ActiveProfiles("test")
class MatriculaRepositoryTests {

    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Test
    void shouldReturnTrue_whenEnrollmentExistsForAlunoAndDisciplina() {
        Aluno aluno = new Aluno();
        aluno.setNome("RepoAluno");
        aluno.setMatricula("REPO1");
        aluno.setStatus(AlunoStatus.ATIVO);
        aluno = alunoRepository.save(aluno);

        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo("REPO-DISC");
        disciplina.setNome("RepoDisciplina");
        disciplina = disciplinaRepository.save(disciplina);

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setSituacao(MatriculaSituacao.CURSANDO);
        matricula.setDataMatricula(LocalDate.now());
        matriculaRepository.save(matricula);

        Assertions.assertTrue(matriculaRepository.existsByAlunoIdAndDisciplinaId(aluno.getId(), disciplina.getId()));
    }
}
