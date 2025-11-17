package br.edu.ifce.jacademico.entities;

// Matricula: enrollment entity linking students and courses with unique constraint and creator tracking.
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "matriculas", uniqueConstraints = {
        @UniqueConstraint(name = "uk_matricula_aluno_disciplina", columnNames = {"aluno_id", "disciplina_id"})
})
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    private LocalDate dataMatricula = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @NotNull
    private MatriculaSituacao situacao = MatriculaSituacao.CURSANDO;

    private Double notaFinal;

    private String createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
