package br.com.meta3.java.scaffold.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

/*
 TODO: (REVIEW) Preserve legacy JSON contract while using Java camelCase conventions.
 // Decision: clients expect the JSON property "nomearquivo" (legacy). To modernize the DTO
 // field to camelCase (nomeArquivo) we keep the external contract by annotating the field
 // with @JsonProperty("nomearquivo"). This keeps API compatibility while following
 // Java naming conventions internally.
 // NewMapper.mapLegacyToDto(null)
*/

/*
 TODO: (REVIEW) Add Jakarta Validation to ensure input adheres to expectations.
 // Decision: add @NotBlank to validate incoming requests at the controller boundary
 // so downstream services/entities can rely on non-empty value.
 // ValidationEngine.validate(request)
*/

public class ArquivoDto {

    // Keep internal field name in camelCase for consistency across the codebase.
    // Preserve the external JSON property name "nomearquivo" to maintain API contract.
    @JsonProperty("nomearquivo")
    @NotBlank(message = "nomearquivo must not be blank")
    private String nomeArquivo;

    public ArquivoDto() {
    }

    public ArquivoDto(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    // Getter follows Java bean naming convention for camelCase field.
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    // Setter follows Java bean naming convention for camelCase field.
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArquivoDto)) return false;
        ArquivoDto that = (ArquivoDto) o;
        return Objects.equals(nomeArquivo, that.nomeArquivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeArquivo);
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "nomeArquivo='" + nomeArquivo + '\'' +
                '}';
    }
}