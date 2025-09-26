package br.com.meta3.java.scaffold.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Data Transfer Object for Arquivo.
 * This DTO is used in API layer to carry file metadata between client and server.
 *
 * Note: We add 'anovigencia' to carry the legacy field through the API layer,
 * keeping DTO <-> Entity mapping consistent with the domain model.
 */
public class ArquivoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank
    private String nome;

    private String tipo;

    private Long tamanho;

    // New property introduced to mirror the legacy domain/entity field.
    // It will be carried through API requests/responses so mapping remains consistent.
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

    public String getAnovigencia() {
        return anovigencia;
    }

    // TODO: (REVIEW) Added 'anovigencia' property to mirror legacy entity and ensure it flows through the API layer
    // The following assignment preserves the legacy setter behavior by directly setting the DTO field.
    public void setAnovigencia(String anovigencia){
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