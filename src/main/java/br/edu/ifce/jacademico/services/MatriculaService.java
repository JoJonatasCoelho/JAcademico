package br.edu.ifce.jacademico.services;

// MatriculaService: handles enrollment creation with duplicate prevention and auditing.
import br.edu.ifce.jacademico.dtos.MatriculaDto;
import br.edu.ifce.jacademico.entities.Aluno;
import br.edu.ifce.jacademico.entities.Disciplina;
import br.edu.ifce.jacademico.entities.Matricula;
import br.edu.ifce.jacademico.repositories.MatriculaRepository;
import br.edu.ifce.jacademico.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            AlunoService alunoService,
                            DisciplinaService disciplinaService) {
        this.matriculaRepository = matriculaRepository;
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
    }

    @Transactional(readOnly = true)
    public List<Matricula> listar() {
        return matriculaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Matricula buscarPorId(Long id) {
        return matriculaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada"));
    }

    @Transactional
    public Matricula salvar(MatriculaDto dto) {
        if (matriculaRepository.existsByAlunoIdAndDisciplinaId(dto.getAlunoId(), dto.getDisciplinaId())) {
            if (dto.getId() == null || !buscarPorId(dto.getId()).getAluno().getId().equals(dto.getAlunoId())
                    || !buscarPorId(dto.getId()).getDisciplina().getId().equals(dto.getDisciplinaId())) {
                throw new IllegalStateException("Matrícula duplicada para o mesmo aluno e disciplina");
            }
        }
        Matricula matricula = dto.getId() != null ? buscarPorId(dto.getId()) : new Matricula();
        Aluno aluno = alunoService.buscarPorId(dto.getAlunoId());
        Disciplina disciplina = disciplinaService.buscarPorId(dto.getDisciplinaId());
        if (matricula.getCreatedBy() == null) {
            matricula.setCreatedBy(SecurityUtils.currentUsername());
        }
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setDataMatricula(dto.getDataMatricula());
        matricula.setSituacao(dto.getSituacao());
        matricula.setNotaFinal(dto.getNotaFinal());
        return matriculaRepository.save(matricula);
    }

    @Transactional
    public void remover(Long id) {
        matriculaRepository.delete(buscarPorId(id));
    }
}
