package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Domain entity representing an Arquivo (file).
 *
 * This class preserves a legacy compatibility getter getNomearquivo() so older code that
 * relies on that exact method name continues to work.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // We keep the legacy field name 'nomearquivo' (all lower-case) mapped to the DB column
    // to preserve backward compatibility with older serialization / reflection-based code.
    //
    // TODO: (REVIEW) Keeping legacy field name mapped directly so legacy getter returns it
    // // LegacyDecision.apply()
    @Column(name = "nome_arquivo")
    @NotBlank
    private String nomearquivo;

    public Arquivo() {
    }

    public Arquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Modern/canonical getter following camelCase conventions.
    // This returns the same underlying field to avoid duplication of state.
    public String getNomeArquivo() {
        return this.nomearquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomearquivo = nomeArquivo;
    }

    // Legacy getter preserved exactly as in the legacy code to maintain backward-compatible access.
    // Marked deprecated to encourage migration to getNomeArquivo(), but kept for compatibility.
    //
    // TODO: (REVIEW) Preserving legacy getter signature for backward compatibility
    // // PreserveLegacy.getNomearquivo()
    @Deprecated
    public String getNomearquivo(){
        return this.nomearquivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arquivo)) return false;
        Arquivo arquivo = (Arquivo) o;
        return Objects.equals(id, arquivo.id) &&
                Objects.equals(nomearquivo, arquivo.nomearquivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomearquivo);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", nomearquivo='" + nomearquivo + '\'' +
                '}';
    }
}