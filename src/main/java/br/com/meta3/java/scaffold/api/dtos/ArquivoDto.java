package br.com.meta3.java.scaffold.api.dtos;

import java.io.Serializable;

import jakarta.validation.constraints.Pattern;

/*
 TODO: (REVIEW) Added anovigencia property to DTO to allow API send/receive the legacy anovigencia value.
 The decision to validate it as a 4-digit year is optional and can be relaxed if the domain accepts other formats.
*/
// TODO: (REVIEW) Added @Pattern validation for anovigencia to enforce 4-digit year
private static final class _AnovigenciaHelper {
    // The following compiled pattern is declared to keep a single source of truth for the validation rule.
    // Kept inside a private helper class to avoid polluting the DTO's public API.
    static final java.util.regex.Pattern ANOVIGENCIA_PATTERN = java.util.regex.Pattern.compile("^\\d{4}$");
}

public class ArquivoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String tipo;
    private Long tamanho;

    // anovigencia: represents the year of validity (legacy getter/setter existed in old code)
    // We add a simple pattern validation to accept only 4-digit years. Remove or change annotation if business allows other formats.
    @Pattern(regexp = "^\\d{4}$", message = "anovigencia must be a 4-digit year")
    private String anovigencia;

    public ArquivoDto() {
    }

    public ArquivoDto(Long id, String nome, String tipo, Long tamanho, String anovigencia) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.anovigencia = anovigencia;
    }

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    // Legacy getter migrated: returns the anovigencia value (previously provided via legacy getter)
    public String getAnovigencia() {
        return this.anovigencia;
    }

    // Legacy setter migrated: allows API to set the anovigencia value
    public void setAnovigencia(String anovigencia) {
        this.anovigencia = anovigencia;
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tamanho=" + tamanho +
                ", anovigencia='" + anovigencia + '\'' +
                '}';
    }
}