package br.edu.ifce.jacademico.services;

// AlunoServiceTests: validates student save and duplicate matricula rule using H2.
import br.edu.ifce.jacademico.dtos.AlunoDto;
import br.edu.ifce.jacademico.entities.Aluno;
import br.edu.ifce.jacademico.entities.AlunoStatus;
import br.edu.ifce.jacademico.repositories.AlunoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AlunoServiceTests {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    void shouldCreateAluno_whenDataIsValid() {
        AlunoDto dto = new AlunoDto();
        dto.setNome("Fulano Teste");
        dto.setMatricula("2024001");
        dto.setEmail("teste@ifce.edu");
        dto.setStatus(AlunoStatus.ATIVO);

        Aluno aluno = alunoService.salvar(dto);

        Assertions.assertNotNull(aluno.getId());
        Assertions.assertEquals("Fulano Teste", aluno.getNome());
    }

    @Test
    void shouldFail_whenMatriculaDuplicate() {
        AlunoDto dto = new AlunoDto();
        dto.setNome("Primeiro");
        dto.setMatricula("DUP001");
        dto.setEmail("p@ifce.edu");
        alunoService.salvar(dto);

        AlunoDto dup = new AlunoDto();
        dup.setNome("Segundo");
        dup.setMatricula("DUP001");

        Assertions.assertThrows(IllegalStateException.class, () -> alunoService.salvar(dup));
        Assertions.assertEquals(1, alunoRepository.count());
    }
}
