package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 TODO: (REVIEW) Renamed legacy field 'nomearquivo' to 'nomeArquivo' to follow Java camelCase.
 Keeping DB column name and JSON property stable to preserve backwards compatibility.
 The @Column(name = "nomearquivo") ensures the database column remains 'nomearquivo'.
 The @JsonProperty("nomearquivo") ensures JSON payloads still use 'nomearquivo'.
 The @NotBlank preserves previous validation semantics.
 A deprecated legacy setter 'setNomearquivo' is provided to avoid breaking older callers,
 delegating to the new camelCase setter.
*/

@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nomearquivo", nullable = false)
    @JsonProperty("nomearquivo")
    private String nomeArquivo;

    public Arquivo() {
    }

    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Primary getter following Java conventions
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    // Primary setter following Java conventions
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    // TODO: (REVIEW) Providing deprecated legacy setter 'setNomearquivo' delegating to new 'setNomeArquivo' to preserve legacy usages.
    /**
     * @deprecated Use {@link #setNomeArquivo(String)} instead.
     */
    @Deprecated
    public void setNomearquivo(String nomearquivo) {
        this.setNomeArquivo(nomearquivo);
    }
}