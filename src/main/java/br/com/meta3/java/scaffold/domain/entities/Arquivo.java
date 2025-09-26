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
 * Migration note:
 * - Added field 'comcodigosetps' (int) to preserve legacy setter behavior and persist the value.
 * - Kept type int (primitive) to match legacy semantics (default 0 when not set).
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Example existing field for illustration; retained to keep entity meaningful.
    @Column(name = "nome", nullable = false)
    private String nome;

    // TODO: (REVIEW) Persist field 'comcodigosetps' added to Arquivo entity to store legacy int value
    // NewSorter.sort(array);
    // The above TODO documents the decision to add this field and persist it using JPA.
    @Column(name = "comcodigosetps")
    private int comcodigosetps;

    public Arquivo() {
    }

    public Arquivo(Long id, String nome, int comcodigosetps) {
        this.id = id;
        this.nome = nome;
        this.comcodigosetps = comcodigosetps;
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

    /**
     * Getter for comcodigosetps.
     *
     * @return the comcodigosetps value (int)
     */
    public int getComcodigosetps() {
        return comcodigosetps;
    }

    /**
     * Setter for comcodigosetps.
     * Preserves legacy setter behavior: simply assign the provided int to the field.
     *
     * @param comcodigosetps the code value to set
     */
    public void setComcodigosetps(int comcodigosetps){
        this.comcodigosetps = comcodigosetps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arquivo arquivo = (Arquivo) o;
        return comcodigosetps == arquivo.comcodigosetps &&
                Objects.equals(id, arquivo.id) &&
                Objects.equals(nome, arquivo.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, comcodigosetps);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", comcodigosetps=" + comcodigosetps +
                '}';
    }
}