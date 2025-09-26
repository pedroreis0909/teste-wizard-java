package br.com.meta3.java.scaffold.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Data Transfer Object for Arquivo used by API layer.
 * Contains semdocumento so the API can transfer this information between client and service layers.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArquivoDto {

    private Long id;
    private String nome;

    // TODO: (REVIEW) Using Integer for semdocumento instead of primitive int to allow nullable state
    // TODO: (REVIEW) Integer semdocumento used to represent optional document number coming from legacy code getter that returned primitive int
    @JsonProperty("semdocumento")
    private Integer semdocumento;

    public ArquivoDto() {
        // default constructor for serialization frameworks
    }

    /**
     * Full constructor including semdocumento to ensure API-layer objects carry this field through.
     */
    public ArquivoDto(Long id, String nome, Integer semdocumento) {
        this.id = id;
        this.nome = nome;
        this.semdocumento = semdocumento;
    }

    /**
     * Convenience constructor without id (for create requests).
     */
    public ArquivoDto(String nome, Integer semdocumento) {
        this.nome = nome;
        this.semdocumento = semdocumento;
    }

    // Builder pattern to facilitate construction without introducing external dependencies like Lombok
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String nome;
        private Integer semdocumento;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder semdocumento(Integer semdocumento) {
            this.semdocumento = semdocumento;
            return this;
        }

        public ArquivoDto build() {
            return new ArquivoDto(id, nome, semdocumento);
        }
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

    // Migrated getter from legacy code: changed to return Integer to support nullability and align with DTO usage
    public Integer getSemdocumento() {
        return this.semdocumento;
    }

    public void setSemdocumento(Integer semdocumento) {
        this.semdocumento = semdocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArquivoDto that = (ArquivoDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(semdocumento, that.semdocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, semdocumento);
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", semdocumento=" + semdocumento +
                '}';
    }
}