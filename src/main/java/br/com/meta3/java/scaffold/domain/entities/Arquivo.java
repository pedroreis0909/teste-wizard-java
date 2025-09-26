package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

/**
 * Domain entity representing an Arquivo.
 *
 * Migration notes:
 * - Added camelCase property 'quantidadeRegistro' mapped to DB column 'quantidaderegistro'.
 * - Added validation @Min(0) to ensure non-negative values.
 * - Kept a deprecated legacy setter 'setQuantidaderegistro' delegating to the new setter
 *   to preserve backward compatibility with any code that may still call the old method.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: (REVIEW) Using setQuantidadeRegistro for setQuantidaderegistro in legacy code
    // setQuantidadeRegistro(value)
    // The decision above preserves a camelCase Java convention while mapping to the old DB column name.

    /**
     * New camelCase property to follow Java naming conventions.
     * Mapped explicitly to the legacy DB column "quantidaderegistro".
     */
    @Column(name = "quantidaderegistro")
    @Min(0)
    private int quantidadeRegistro;

    public Arquivo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidadeRegistro() {
        return quantidadeRegistro;
    }

    /**
     * Standardized setter using camelCase naming.
     *
     * @param quantidadeRegistro non-negative quantity of registros
     */
    public void setQuantidadeRegistro(int quantidadeRegistro) {
        this.quantidadeRegistro = quantidadeRegistro;
    }

    // TODO: (REVIEW) Keep legacy setter signature delegating to new setter for backward compatibility
    // setQuantidadeRegistro(quantidaderegistro)
    /**
     * Deprecated legacy-style setter kept for compatibility with existing code that may
     * call the old method name. Delegates to the standardized setter.
     */
    @Deprecated
    public void setQuantidaderegistro(int quantidaderegistro) {
        // Delegate to the new standardized setter to keep behavior consistent.
        setQuantidadeRegistro(quantidaderegistro);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", quantidadeRegistro=" + quantidadeRegistro +
                '}';
    }
}