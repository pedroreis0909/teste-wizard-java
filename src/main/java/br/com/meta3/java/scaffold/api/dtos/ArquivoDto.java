package br.com.meta3.java.scaffold.api.dtos;

import jakarta.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Data Transfer Object for Arquivo used by API layer.
 * Only fields required by the current migration are included/updated here.
 */
// TODO: (REVIEW) Added comerro to DTO to expose the corresponding domain field to the API layer.
// TODO: (REVIEW) Applied @NotNull to comerro to enforce presence at API validation level. This choice
// TODO: (REVIEW) should be validated against business rules — if comerro can be optional, remove the annotation
// TODO: (REVIEW) and handle nulls in service/repository layers. Kept validation annotation minimal to avoid
// TODO: (REVIEW) changing downstream behavior unexpectedly.
public class ArquivoDto {

    @NotNull
    private Integer comerro;

    public ArquivoDto() {
    }

    public ArquivoDto(Integer comerro) {
        this.comerro = comerro;
    }

    // Preserves legacy setter functionality
    public void setComerro(int comerro) {
        this.comerro = comerro;
    }

    // Overloaded setter to accept Integer as well (helps with frameworks that may pass boxed types)
    public void setComerro(Integer comerro) {
        this.comerro = comerro;
    }

    public Integer getComerro() {
        return comerro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArquivoDto)) return false;
        ArquivoDto that = (ArquivoDto) o;
        return Objects.equals(comerro, that.comerro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comerro);
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "comerro=" + comerro +
                '}';
    }
}