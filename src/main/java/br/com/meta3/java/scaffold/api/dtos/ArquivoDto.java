package br.com.meta3.java.scaffold.api.dtos;

import jakarta.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Data Transfer Object for Arquivo entity used in API layer.
 * Contains only fields necessary to transfer data to/from clients.
 */
public class ArquivoDto {

    private Long id;
    private String nome;

    // TODO: (REVIEW) Using Integer for comcodigosetps instead of primitive int in DTO to allow nullability for partial updates
    // Integer comcodigosetpsPlaceholder = null
    // TODO: (REVIEW) Added @NotNull to enforce presence in API payloads. If this field should be optional for partial updates,
    // TODO: (REVIEW) remove the @NotNull annotation and handle null accordingly in the service layer.
    @NotNull
    private Integer comcodigosetps;

    public ArquivoDto() {
    }

    public ArquivoDto(Long id, String nome, Integer comcodigosetps) {
        this.id = id;
        this.nome = nome;
        this.comcodigosetps = comcodigosetps;
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

    public Integer getComcodigosetps() {
        return comcodigosetps;
    }

    public void setComcodigosetps(Integer comcodigosetps) {
        this.comcodigosetps = comcodigosetps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArquivoDto)) return false;
        ArquivoDto that = (ArquivoDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(comcodigosetps, that.comcodigosetps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, comcodigosetps);
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", comcodigosetps=" + comcodigosetps +
                '}';
    }
}