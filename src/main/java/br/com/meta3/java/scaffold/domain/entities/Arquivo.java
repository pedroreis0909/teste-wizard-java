package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * JPA entity representing Arquivo.
 *
 * Note: This file was updated to include the legacy 'comerro' field (primitive int),
 * annotated with @Column and providing legacy-compatible getter/setter methods.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: (REVIEW) Mapping legacy field 'comerro' to JPA column 'comerro' to preserve legacy getter/setter behavior
    // LegacyMapping.register("comerro"); // (placeholder - review if any custom legacy registration is needed)

    @Column(name = "comerro")
    private int comerro;

    // Default constructor required by JPA
    public Arquivo() {
    }

    public Arquivo(Long id, int comerro) {
        this.id = id;
        this.comerro = comerro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Preserve legacy getter name and behavior
    public int getComerro(){
        return this.comerro;
    }

    // Legacy-compatible setter
    public void setComerro(int comerro) {
        this.comerro = comerro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arquivo arquivo = (Arquivo) o;
        return Objects.equals(id, arquivo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", comerro=" + comerro +
                '}';
    }
}