package br.com.meta3.java.scaffold.domain.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Domain entity representing an Arquivo.
 *
 * Note: This file was updated to add the legacy field 'anovigencia' with
 * appropriate JPA mapping and accessor methods to preserve legacy behavior.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    // Primary identifier for the entity. Kept simple to remain compatible with existing repository code.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: (REVIEW) Mapping legacy field 'anovigencia' to a dedicated DB column.
    // TODO: (REVIEW) Preserve legacy getter behavior by returning the exact field value.
    // TODO: (REVIEW) Using JPA @Column to ensure schema mapping and allow future DB migrations.
    // Decision (pseudo): Map column "anovigencia" as a VARCHAR with length 50 and allow nulls for compatibility.
    // TODO-CODE (REVIEW): this.anovigencia = anovigencia;  // (kept as illustrative decision - not executable here)

    /**
     * Legacy field added during migration.
     * - Kept as String to preserve legacy behavior where getAnovigencia() simply returns the stored value.
     * - Column length chosen moderately (50) to accommodate typical year/version strings, nullable to avoid breaking existing inserts.
     */
    @Column(name = "anovigencia", length = 50, nullable = true)
    private String anovigencia;

    // Default constructor required by JPA
    public Arquivo() {
    }

    // Convenience constructor
    public Arquivo(Long id, String anovigencia) {
        this.id = id;
        this.anovigencia = anovigencia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Legacy-compatible getter: returns the stored anovigencia value.
    public String getAnovigencia(){
        return this.anovigencia;
    }

    // Legacy-compatible setter: allows setting the anovigencia value.
    public void setAnovigencia(String anovigencia) {
        this.anovigencia = anovigencia;
    }

    // equals/hashCode/toString implementations to help with debugging and entity management.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arquivo arquivo = (Arquivo) o;

        return id != null ? id.equals(arquivo.id) : arquivo.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", anovigencia='" + anovigencia + '\'' +
                '}';
    }
}