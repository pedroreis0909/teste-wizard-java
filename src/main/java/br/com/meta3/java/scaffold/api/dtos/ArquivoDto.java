package br.com.meta3.java.scaffold.api.dtos;

import jakarta.validation.constraints.Min;
import java.util.Objects;

/**
 * Data Transfer Object for Arquivo entity used by controllers and services.
 * Only basic fields are present to represent file metadata and the migrated
 * quantidadeRegistro property.
 */
public class ArquivoDto {

    private Long id;
    private String nome;
    private String tipo;
    private Long tamanho;

    // New property migrated from legacy code.
    // Applying Jakarta Validation to ensure non-negative values.
    @Min(0)
    private Integer quantidadeRegistro;

    public ArquivoDto() {
    }

    public ArquivoDto(Long id, String nome, String tipo, Long tamanho, Integer quantidadeRegistro) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.quantidadeRegistro = quantidadeRegistro;
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

    public Integer getQuantidadeRegistro() {
        return quantidadeRegistro;
    }

    public void setQuantidadeRegistro(Integer quantidadeRegistro) {
        this.quantidadeRegistro = quantidadeRegistro;
    }

    // TODO: (REVIEW) Migrated legacy setter name `setQuantidaderegistro(int)` to
    // `setQuantidadeRegistro(Integer)`. Kept an adapter deprecated method to preserve
    // backward compatibility with any legacy callers that may still use the old name.
    // The legacy signature used a primitive int; here we accept int and delegate to the
    // new boxed Integer setter to allow nullability in modern DTO usage.
    @Deprecated
    public void setQuantidaderegistro(int quantidaderegistro) {
        // Delegate to the new setter to centralize validation/assignment logic.
        this.setQuantidadeRegistro(quantidaderegistro);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArquivoDto)) return false;
        ArquivoDto that = (ArquivoDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(tipo, that.tipo) &&
                Objects.equals(tamanho, that.tamanho) &&
                Objects.equals(quantidadeRegistro, that.quantidadeRegistro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, tipo, tamanho, quantidadeRegistro);
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tamanho=" + tamanho +
                ", quantidadeRegistro=" + quantidadeRegistro +
                '}';
    }
}