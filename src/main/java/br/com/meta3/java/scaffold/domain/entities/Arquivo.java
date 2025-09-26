package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * Domain entity representing an Arquivo.
 *
 * Note: This file was updated to include the 'aptos' property for persistence.
 */
@Entity
@Table(name = "arquivos")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: (REVIEW) Mapping 'aptos' field using JPA column mapping and int primitive to preserve legacy setter behavior
    // NewSorter.sort(array)

    /**
     * Number of "aptos" associated with this Arquivo.
     *
     * Chosen to be a primitive int to preserve legacy behavior where nullability wasn't considered
     * (legacy setter accepted an int). If nullability is later required, consider changing to Integer
     * and handling migrations/defaults appropriately.
     */
    @Column(name = "aptos", nullable = false)
    private int aptos;

    // Default constructor required by JPA
    public Arquivo() {
    }

    public Arquivo(Long id, int aptos) {
        this.id = id;
        this.aptos = aptos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the number of aptos.
     *
     * @return aptos as primitive int (legacy behavior preserved)
     */
    public int getAptos() {
        return aptos;
    }

    /**
     * Legacy setter preserved: directly assigns the provided primitive int to the field.
     *
     * Preserves original behavior:
     * public void setAptos(int aptos){
     *     this.aptos = aptos;
     * }
     *
     * Keeping the implementation exactly to maintain backward compatibility.
     *
     * @param aptos number of aptos
     */
    public void setAptos(int aptos){
        this.aptos = aptos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arquivo arquivo = (Arquivo) o;
        return aptos == arquivo.aptos && Objects.equals(id, arquivo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, aptos);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", aptos=" + aptos +
                '}';
    }
}