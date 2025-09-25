package br.com.meta3.java.scaffold.api.dtos;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for Arquivo used by API layer.
 * Provides validation and mapping helpers to/from domain entity.
 */
public class ArquivoDto {

    // TODO: (REVIEW) Map JSON 'nomearquivo' to DTO property 'nomeArquivo' for backward compatibility
    @JsonProperty("nomearquivo")
    @NotBlank(message = "nomeArquivo must not be blank")
    private String nomeArquivo;

    public ArquivoDto() {
    }

    public ArquivoDto(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    /**
     * Map this DTO to the domain entity.
     * Note: the domain entity still uses the legacy-named setter 'setNomearquivo'.
     */
    public Arquivo toEntity() {
        Arquivo a = new Arquivo();
        // TODO: (REVIEW) Using legacy setter 'setNomearquivo' on Arquivo entity to keep compatibility
        a.setNomearquivo(this.nomeArquivo);
        return a;
    }

    /**
     * Create a DTO from the domain entity.
     * Note: the domain entity may expose legacy-named getter 'getNomearquivo'.
     */
    public static ArquivoDto fromEntity(Arquivo arquivo) {
        if (arquivo == null) {
            return null;
        }
        ArquivoDto dto = new ArquivoDto();
        // TODO: (REVIEW) Mapping from entity's legacy getter 'getNomearquivo' to DTO 'nomeArquivo'
        dto.setNomeArquivo(arquivo.getNomearquivo());
        return dto;
    }
}