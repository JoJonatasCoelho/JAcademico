package br.edu.ifce.jacademico.controllers;

// MatriculaController: manages enrollment screens and enforces duplicate rule feedback.
import br.edu.ifce.jacademico.dtos.MatriculaDto;
import br.edu.ifce.jacademico.entities.Matricula;
import br.edu.ifce.jacademico.entities.MatriculaSituacao;
import br.edu.ifce.jacademico.services.AlunoService;
import br.edu.ifce.jacademico.services.DisciplinaService;
import br.edu.ifce.jacademico.services.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;

    public MatriculaController(MatriculaService matriculaService, AlunoService alunoService, DisciplinaService disciplinaService) {
        this.matriculaService = matriculaService;
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("matriculas", matriculaService.listar());
        return "matriculas/list";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        prepararFormulario(model, new MatriculaDto());
        return "matriculas/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Matricula matricula = matriculaService.buscarPorId(id);
        MatriculaDto dto = new MatriculaDto();
        dto.setId(matricula.getId());
        dto.setAlunoId(matricula.getAluno().getId());
        dto.setDisciplinaId(matricula.getDisciplina().getId());
        dto.setDataMatricula(matricula.getDataMatricula());
        dto.setSituacao(matricula.getSituacao());
        dto.setNotaFinal(matricula.getNotaFinal());
        prepararFormulario(model, dto);
        return "matriculas/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("matricula") MatriculaDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            prepararFormulario(model, dto);
            return "matriculas/form";
        }
        try {
            matriculaService.salvar(dto);
            return "redirect:/matriculas";
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            prepararFormulario(model, dto);
            return "matriculas/form";
        }
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable("id") Long id) {
        matriculaService.remover(id);
        return "redirect:/matriculas";
    }

    private void prepararFormulario(Model model, MatriculaDto dto) {
        model.addAttribute("matricula", dto);
        model.addAttribute("alunos", alunoService.listar());
        model.addAttribute("disciplinas", disciplinaService.listar());
        model.addAttribute("situacoes", MatriculaSituacao.values());
    }
}
