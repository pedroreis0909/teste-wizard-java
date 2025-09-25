package br.com.meta3.java.scaffold.api.dtos;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for Arquivo used in API layer.
 * Keeps the legacy field name 'codigoarquivo' to preserve API compatibility with existing clients.
 */
public class ArquivoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO: (REVIEW) Choosing Integer instead of primitive int for the DTO field to allow nullable values
    // This allows incoming requests to omit the field (null) when appropriate while still keeping compatibility.
    // The legacy setter with primitive int is preserved below as an overload to maintain binary/backwards compatibility.
    @NotNull
    private Integer codigoarquivo;

    public ArquivoDto() {
    }

    public ArquivoDto(Integer codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    /**
     * Getter for codigoarquivo to mirror the domain entity naming and preserve API contract.
     */
    public Integer getCodigoarquivo() {
        return codigoarquivo;
    }

    /**
     * Setter using Integer to support typical JSON deserialization and nullability.
     */
    public void setCodigoarquivo(Integer codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    // TODO: (REVIEW) Preserving legacy primitive setter signature to match legacy code usage
    // The original legacy code provided: public void setCodigoarquivo(int codigoarquivo){ this.codigoarquivo = codigoarquivo; }
    // Keep this overload so existing code that calls the primitive-typed setter will continue to work unchanged.
    public void setCodigoarquivo(int codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    // TODO: (REVIEW) Keeping field name exactly as in the domain entity ('codigoarquivo') to ensure mapping consistency
    // This decision avoids mapping surprises and keeps DTO <-> Entity conversion straightforward.
}