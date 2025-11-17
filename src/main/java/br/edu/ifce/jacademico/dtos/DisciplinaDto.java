package br.edu.ifce.jacademico.dtos;

// DisciplinaDto: transports course data for forms with basic validation.
import jakarta.validation.constraints.NotBlank;

public class DisciplinaDto {
    private Long id;

    @NotBlank
    private String codigo;

    @NotBlank
    private String nome;

    private Integer cargaHoraria;

    private String semestre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
}
