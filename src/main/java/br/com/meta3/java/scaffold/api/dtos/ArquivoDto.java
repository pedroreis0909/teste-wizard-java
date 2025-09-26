package br.com.meta3.java.scaffold.api.dtos;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for Arquivo API.
 * Contains fields exchanged with the API layer.
 */
public class ArquivoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO: (REVIEW) Changed comerro from primitive int in legacy getter to Integer with @NotNull in DTO
    // TODO: (REVIEW) Legacy code had: public int getComerro(){ return this.comerro; }
    // TODO: (REVIEW) Using wrapper Integer allows proper validation and JSON null handling in requests/responses
    // New decision: keep value as Integer and enforce presence via @NotNull for incoming API payloads
    @NotNull(message = "comerro must not be null")
    private Integer comerro;

    public ArquivoDto() {
    }

    public ArquivoDto(Integer comerro) {
        this.comerro = comerro;
    }

    public Integer getComerro() {
        return this.comerro;
    }

    public void setComerro(Integer comerro) {
        this.comerro = comerro;
    }

}