package br.com.meta3.java.scaffold.api.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for Arquivo used in API layer.
 * Added comcodigosetps to carry legacy field from domain/entity to API.
 */
public class ArquivoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO: (REVIEW) Using primitive int for comcodigosetps to match legacy getter signature
    // NOTE: If nullability is required in the future, consider changing this to Integer and
    //       updating mappings accordingly.
    // comcodigosetps example usage (illustrative):
    // getComcodigosetps();
    @JsonProperty("comcodigosetps")
    private int comcodigosetps;

    public ArquivoDto() {
    }

    public ArquivoDto(int comcodigosetps) {
        this.comcodigosetps = comcodigosetps;
    }

    public int getComcodigosetps() {
        return this.comcodigosetps;
    }

    public void setComcodigosetps(int comcodigosetps) {
        this.comcodigosetps = comcodigosetps;
    }
}