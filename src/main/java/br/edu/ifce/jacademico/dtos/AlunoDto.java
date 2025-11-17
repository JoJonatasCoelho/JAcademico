package br.edu.ifce.jacademico.dtos;

// AlunoDto: carries student data between view and service layers with validation.
import br.edu.ifce.jacademico.entities.AlunoStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class AlunoDto {
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String matricula;

    @Email
    private String email;

    private LocalDate dataNascimento;

    private AlunoStatus status = AlunoStatus.ATIVO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public AlunoStatus getStatus() {
        return status;
    }

    public void setStatus(AlunoStatus status) {
        this.status = status;
    }
}
