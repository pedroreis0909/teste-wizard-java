package br.com.meta3.java.scaffold.api.dtos;

import jakarta.validation.constraints.Min;
import java.util.Objects;

/**
 * Data Transfer Object for Arquivo entity used by API layer.
 * Only essential fields are present here; other fields can be added as needed.
 */
// TODO: (REVIEW) Using Integer for aptos instead of primitive int to allow distinguishing omitted values
// and better interplay with validation (null will be treated as "not provided").
// This decision helps the API distinguish between "not provided" and "explicitly zero".
public class ArquivoDto {

    private Long id;
    private String nome;

    // Validate that aptos, when provided, is not negative.
    @Min(0)
    private Integer aptos;

    public ArquivoDto() {
    }

    public ArquivoDto(Long id, String nome, Integer aptos) {
        this.id = id;
        this.nome = nome;
        this.aptos = aptos;
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

    public Integer getAptos() {
        return aptos;
    }

    // TODO: (REVIEW) Migrated legacy setter from legacy code:
    // Legacy:
    // public void setAptos(int aptos){
    //     this.aptos = aptos;
    // }
    //
    // Kept an overloaded setter that accepts primitive int to preserve compatibility with legacy call sites
    // while preferring the Integer-based setter for nullability/validation behavior.
    public void setAptos(Integer aptos) {
        this.aptos = aptos;
    }

    // Overloaded setter for compatibility with code that may pass primitive int (legacy style).
    public void setAptos(int aptos) {
        this.aptos = aptos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArquivoDto)) return false;
        ArquivoDto that = (ArquivoDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(aptos, that.aptos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, aptos);
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", aptos=" + aptos +
                '}';
    }
}