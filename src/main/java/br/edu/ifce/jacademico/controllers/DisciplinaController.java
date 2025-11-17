package br.edu.ifce.jacademico.controllers;

// DisciplinaController: handles course CRUD views and business-rule errors.
import br.edu.ifce.jacademico.dtos.DisciplinaDto;
import br.edu.ifce.jacademico.entities.Disciplina;
import br.edu.ifce.jacademico.services.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("disciplinas", disciplinaService.listar());
        return "disciplinas/list";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("disciplina", new DisciplinaDto());
        return "disciplinas/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Disciplina disciplina = disciplinaService.buscarPorId(id);
        DisciplinaDto dto = new DisciplinaDto();
        dto.setId(disciplina.getId());
        dto.setCodigo(disciplina.getCodigo());
        dto.setNome(disciplina.getNome());
        dto.setCargaHoraria(disciplina.getCargaHoraria());
        dto.setSemestre(disciplina.getSemestre());
        model.addAttribute("disciplina", dto);
        return "disciplinas/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("disciplina") DisciplinaDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "disciplinas/form";
        }
        try {
            disciplinaService.salvar(dto);
            return "redirect:/disciplinas";
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "disciplinas/form";
        }
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable Long id, Model model) {
        try {
            disciplinaService.remover(id);
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "redirect:/disciplinas?erro=" + e.getMessage();
        }
        return "redirect:/disciplinas";
    }
}
