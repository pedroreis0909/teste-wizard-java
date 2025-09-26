package br.com.meta3.java.scaffold.api.dtos;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Data Transfer Object for Arquivo used by API layer.
 * Exposes the aptos property to transport it in requests/responses.
 */
// TODO: (REVIEW) Migrated getter/setter behavior from legacy getAptos() implementation.
// The legacy method returned a primitive int; here we use Integer to allow validation and
// clearer presence checks on incoming JSON payloads.
public class ArquivoDto {

    // Using Integer to allow @NotNull validation to be meaningful in request payloads.
    // TODO: (REVIEW) Chose @NotNull + @Min(0) to enforce presence and non-negative value.
    @NotNull(message = "aptos must be provided")
    @Min(value = 0, message = "aptos must be non-negative")
    private Integer aptos;

    public ArquivoDto() {
    }

    public ArquivoDto(Integer aptos) {
        this.aptos = aptos;
    }

    // Expose getter to align with legacy API behavior.
    // Legacy code snippet:
    // public int getAptos(){ return this.aptos; }
    // TODO: (REVIEW) Kept name getAptos to maintain compatibility with frameworks that rely on JavaBean naming.
    public Integer getAptos() {
        return this.aptos;
    }

    // Expose setter to allow request binding and mapping.
    public void setAptos(Integer aptos) {
        this.aptos = aptos;
    }

    /**
     * Map this DTO to domain entity.
     * TODO: (REVIEW) Mapping strategy: entity likely uses primitive int for aptos;
     * convert Integer to int safely using 0 as default if null (shouldn't happen due to @NotNull,
     * but this guards against programmatic usage).
     */
    public Arquivo toEntity() {
        Arquivo arquivo = new Arquivo();
        arquivo.setAptos(this.aptos != null ? this.aptos : 0);
        return arquivo;
    }

    /**
     * Create DTO from domain entity.
     * TODO: (REVIEW) Using entity.getAptos() (primitive int) and autoboxing into Integer for DTO.
     */
    public static ArquivoDto fromEntity(Arquivo arquivo) {
        if (arquivo == null) {
            return null;
        }
        return new ArquivoDto(arquivo.getAptos());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArquivoDto)) return false;
        ArquivoDto that = (ArquivoDto) o;
        return Objects.equals(aptos, that.aptos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aptos);
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "aptos=" + aptos +
                '}';
    }
}