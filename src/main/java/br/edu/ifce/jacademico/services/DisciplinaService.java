package br.edu.ifce.jacademico.services;

// DisciplinaService: enforces course business rules and role-based delete restriction.
import br.edu.ifce.jacademico.dtos.DisciplinaDto;
import br.edu.ifce.jacademico.entities.Disciplina;
import br.edu.ifce.jacademico.repositories.MatriculaRepository;
import br.edu.ifce.jacademico.repositories.DisciplinaRepository;
import br.edu.ifce.jacademico.security.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final MatriculaRepository matriculaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository, MatriculaRepository matriculaRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.matriculaRepository = matriculaRepository;
    }

    @Transactional(readOnly = true)
    public List<Disciplina> listar() {
        return disciplinaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Disciplina buscarPorId(Long id) {
        return disciplinaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
    }

    @Transactional
    public Disciplina salvar(DisciplinaDto dto) {
        disciplinaRepository.findByCodigo(dto.getCodigo()).ifPresent(existing -> {
            if (dto.getId() == null || !existing.getId().equals(dto.getId())) {
                throw new IllegalStateException("Código de disciplina já existe");
            }
        });
        Disciplina disciplina = dto.getId() != null ? buscarPorId(dto.getId()) : new Disciplina();
        if (disciplina.getCreatedBy() == null) {
            disciplina.setCreatedBy(SecurityUtils.currentUsername());
        }
        disciplina.setCodigo(dto.getCodigo());
        disciplina.setNome(dto.getNome());
        disciplina.setCargaHoraria(dto.getCargaHoraria());
        disciplina.setSemestre(dto.getSemestre());
        return disciplinaRepository.save(disciplina);
    }

    @Transactional
    public void remover(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isSecretaria = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SECRETARIA"));
        if (isSecretaria) {
            throw new IllegalStateException("Secretaria não pode excluir disciplinas");
        }
        // Delete enrollments explicitly to avoid unintended cascade behavior.
        matriculaRepository.deleteAllByDisciplinaId(id);
        disciplinaRepository.delete(buscarPorId(id));
    }
}
