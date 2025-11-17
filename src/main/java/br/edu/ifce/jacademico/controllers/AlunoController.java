package br.edu.ifce.jacademico.controllers;

// AlunoController: MVC endpoints for CRUD over students using DTOs and services.
import br.edu.ifce.jacademico.dtos.AlunoDto;
import br.edu.ifce.jacademico.entities.Aluno;
import br.edu.ifce.jacademico.services.AlunoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alunos", alunoService.listar());
        return "alunos/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("aluno", new AlunoDto());
        return "alunos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Aluno aluno = alunoService.buscarPorId(id);
        AlunoDto dto = new AlunoDto();
        dto.setId(aluno.getId());
        dto.setNome(aluno.getNome());
        dto.setMatricula(aluno.getMatricula());
        dto.setEmail(aluno.getEmail());
        dto.setDataNascimento(aluno.getDataNascimento());
        dto.setStatus(aluno.getStatus());
        model.addAttribute("aluno", dto);
        return "alunos/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("aluno") AlunoDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "alunos/form";
        }
        try {
            alunoService.salvar(dto);
            return "redirect:/alunos";
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "alunos/form";
        }
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable("id") Long id, Model model) {
        try {
            alunoService.remover(id);
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "redirect:/alunos";
    }
}
