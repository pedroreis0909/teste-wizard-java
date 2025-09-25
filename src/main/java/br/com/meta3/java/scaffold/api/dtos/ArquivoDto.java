package br.com.meta3.java.scaffold.api.dtos;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

public class ArquivoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código da escola.
     * This field was added to mirror the domain entity and expose it through the API contract.
     */
    @NotBlank(message = "codigoescola is required")
    private String codigoescola;

    public ArquivoDto() {
    }

    public ArquivoDto(String codigoescola) {
        this.codigoescola = codigoescola;
    }

    // TODO: (REVIEW) Migrated legacy getter getCodigoescola() into DTO to expose the field in API contracts
    // TODO: (REVIEW) Ensure validation and naming conventions align with the domain entity and persistence layer
    // NewSorter.sort(array)

    public String getCodigoescola() {
        return this.codigoescola;
    }

    public void setCodigoescola(String codigoescola) {
        this.codigoescola = codigoescola;
    }
}