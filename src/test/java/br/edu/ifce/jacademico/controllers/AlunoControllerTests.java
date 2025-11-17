package br.edu.ifce.jacademico.controllers;

// AlunoControllerTests: verifies list endpoint renders template with model data using MockMvc without security filters.
import br.edu.ifce.jacademico.entities.Aluno;
import br.edu.ifce.jacademico.entities.AlunoStatus;
import br.edu.ifce.jacademico.services.AlunoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AlunoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AlunoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoService alunoService;

    @Test
    void shouldRenderAlunoList() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aluno Mock");
        aluno.setMatricula("M1");
        aluno.setStatus(AlunoStatus.ATIVO);
        Mockito.when(alunoService.listar()).thenReturn(Collections.singletonList(aluno));

        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("alunos/list"))
                .andExpect(model().attributeExists("alunos"));
    }
}
