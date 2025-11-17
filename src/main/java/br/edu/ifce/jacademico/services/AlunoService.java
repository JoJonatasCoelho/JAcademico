package br.edu.ifce.jacademico.services;

// AlunoService: business logic for students with duplication checks and auditing.
import br.edu.ifce.jacademico.dtos.AlunoDto;
import br.edu.ifce.jacademico.entities.Aluno;
import br.edu.ifce.jacademico.repositories.AlunoRepository;
import br.edu.ifce.jacademico.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Transactional(readOnly = true)
    public List<Aluno> listar() {
        return alunoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
    }

    @Transactional
    public Aluno salvar(AlunoDto dto) {
        alunoRepository.findByMatricula(dto.getMatricula()).ifPresent(existing -> {
            if (dto.getId() == null || !existing.getId().equals(dto.getId())) {
                throw new IllegalStateException("Matrícula já cadastrada");
            }
        });
        Aluno aluno = dto.getId() != null ? buscarPorId(dto.getId()) : new Aluno();
        if (aluno.getCreatedBy() == null) {
            aluno.setCreatedBy(SecurityUtils.currentUsername());
        }
        aluno.setNome(dto.getNome());
        aluno.setMatricula(dto.getMatricula());
        aluno.setEmail(dto.getEmail());
        aluno.setDataNascimento(dto.getDataNascimento());
        aluno.setStatus(dto.getStatus());
        return alunoRepository.save(aluno);
    }

    @Transactional
    public void remover(Long id) {
        alunoRepository.delete(buscarPorId(id));
    }
}
