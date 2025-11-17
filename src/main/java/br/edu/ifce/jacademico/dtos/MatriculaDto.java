package br.edu.ifce.jacademico.dtos;

// MatriculaDto: encapsulates enrollment payload for forms with validation hints.
import br.edu.ifce.jacademico.entities.MatriculaSituacao;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class MatriculaDto {
    private Long id;

    @NotNull
    private Long alunoId;

    @NotNull
    private Long disciplinaId;

    private LocalDate dataMatricula = LocalDate.now();

    @NotNull
    private MatriculaSituacao situacao = MatriculaSituacao.CURSANDO;

    private Double notaFinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public MatriculaSituacao getSituacao() {
        return situacao;
    }

    public void setSituacao(MatriculaSituacao situacao) {
        this.situacao = situacao;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }
}
