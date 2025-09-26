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
 * Notes:
 * - Keeps compatibility with legacy setter semantics for 'comerro' (primitive int).
 * - Mapped as a JPA entity to persist basic fields; mapping is intentionally minimal to avoid
 *   touching infrastructure/repository layers in this change.
 */
@Entity
@Table(name = "arquivos")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    // Legacy field preserved. Type is primitive int to match legacy setter semantics.
    @Column(name = "comerro")
    private int comerro;

    public Arquivo() {
    }

    public Arquivo(Long id, String nome, int comerro) {
        this.id = id;
        this.nome = nome;
        this.comerro = comerro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getComerro() {
        return comerro;
    }

    /**
     * Sets the comerro value.
     *
     * <p>
     * This setter preserves the legacy behavior:
     * - Accepts a primitive int (not Integer) because the legacy code used a primitive.
     * - Directly assigns the provided value to the field without additional validation or transformation.
     * </p>
     *
     * @param comerro the comerro value to set
     */
    public void setComerro(int comerro) {
        this.comerro = comerro;
    }

    // TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
    // NewSorter.sort(array)
    // Decision note: preserved primitive 'int' to maintain legacy behavior (no nullability).
    // If future requirements need nullable values, consider migrating to Integer and adding validation.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arquivo)) return false;
        Arquivo arquivo = (Arquivo) o;
        return comerro == arquivo.comerro &&
                Objects.equals(id, arquivo.id) &&
                Objects.equals(nome, arquivo.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, comerro);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", comerro=" + comerro +
                '}';
    }
}