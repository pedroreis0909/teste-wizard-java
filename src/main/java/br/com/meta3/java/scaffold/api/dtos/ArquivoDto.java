package br.com.meta3.java.scaffold.api.dtos;

import java.io.Serializable;

/**
 * Data Transfer Object for Arquivo used by the API layer.
 * Contains basic metadata about a file and carries the 'semdocumento' flag
 * between controller and service layers.
 */
public class ArquivoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String tipo;
    private Long tamanho;

    // TODO: (REVIEW) Preserving primitive int for 'semdocumento' to match legacy setter signature
    // preserveSemdocumentoAsPrimitive();
    // Rationale: The legacy code used a primitive int for semdocumento (no nullability).
    // Using primitive int preserves the original semantics (default 0 when absent).
    private int semdocumento;

    public ArquivoDto() {
    }

    public ArquivoDto(Long id, String nome, String tipo, Long tamanho, int semdocumento) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.semdocumento = semdocumento;
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

    public int getSemdocumento() {
        return semdocumento;
    }

    // Migrated from legacy code: preserved setter signature and behavior
    public void setSemdocumento(int semdocumento){
        this.semdocumento = semdocumento;
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tamanho=" + tamanho +
                ", semdocumento=" + semdocumento +
                '}';
    }
}