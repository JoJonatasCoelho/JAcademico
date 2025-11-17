package br.edu.ifce.jacademico.services;

// MatriculaServiceTests: ensures duplicate enrollments are blocked and basic persistence works.
import br.edu.ifce.jacademico.dtos.AlunoDto;
import br.edu.ifce.jacademico.dtos.DisciplinaDto;
import br.edu.ifce.jacademico.dtos.MatriculaDto;
import br.edu.ifce.jacademico.entities.Matricula;
import br.edu.ifce.jacademico.entities.MatriculaSituacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MatriculaServiceTests {

    @Autowired
    private MatriculaService matriculaService;
    @Autowired
    private AlunoService alunoService;
    @Autowired
    private DisciplinaService disciplinaService;

    private Long criarAluno() {
        AlunoDto aluno = new AlunoDto();
        aluno.setNome("Aluno");
        aluno.setMatricula("MAT-TST");
        return alunoService.salvar(aluno).getId();
    }

    private Long criarDisciplina() {
        DisciplinaDto disciplina = new DisciplinaDto();
        disciplina.setCodigo("DISC-TST");
        disciplina.setNome("Teste");
        return disciplinaService.salvar(disciplina).getId();
    }

    @Test
    void shouldCreateMatricula_whenValid() {
        Long alunoId = criarAluno();
        Long disciplinaId = criarDisciplina();

        MatriculaDto dto = new MatriculaDto();
        dto.setAlunoId(alunoId);
        dto.setDisciplinaId(disciplinaId);
        dto.setSituacao(MatriculaSituacao.CURSANDO);

        Matricula matricula = matriculaService.salvar(dto);
        Assertions.assertNotNull(matricula.getId());
        Assertions.assertEquals(MatriculaSituacao.CURSANDO, matricula.getSituacao());
    }

    @Test
    void shouldRejectDuplicateMatricula_forSameAlunoAndDisciplina() {
        Long alunoId = criarAluno();
        Long disciplinaId = criarDisciplina();

        MatriculaDto dto = new MatriculaDto();
        dto.setAlunoId(alunoId);
        dto.setDisciplinaId(disciplinaId);
        matriculaService.salvar(dto);

        MatriculaDto duplicada = new MatriculaDto();
        duplicada.setAlunoId(alunoId);
        duplicada.setDisciplinaId(disciplinaId);

        Assertions.assertThrows(IllegalStateException.class, () -> matriculaService.salvar(duplicada));
    }
}
