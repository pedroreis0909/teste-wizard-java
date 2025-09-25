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
 * Kept intentionally simple to preserve legacy semantics required by services and tests.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    // Legacy field name preserved to maintain compatibility with existing code/tests.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigoarquivo")
    private int codigoarquivo;

    // Example additional field kept minimal; can be expanded later if needed.
    @Column(name = "nome")
    private String nome;

    public Arquivo() {
    }

    public Arquivo(int codigoarquivo, String nome) {
        this.codigoarquivo = codigoarquivo;
        this.nome = nome;
    }

    // TODO: (REVIEW) Added legacy-style getter getCodigoarquivo to preserve compatibility
    // with existing services/tests that rely on the exact method name and return type.
    // Using primitive int to match the legacy implementation; consider migrating to Integer
    // if nullability/ORM semantics require it in the future.
    public int getCodigoarquivo(){
        return this.codigoarquivo;
    }

    public void setCodigoarquivo(int codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Standard equals/hashCode for entity identity comparisons.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arquivo)) return false;
        Arquivo arquivo = (Arquivo) o;
        return codigoarquivo == arquivo.codigoarquivo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoarquivo);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "codigoarquivo=" + codigoarquivo +
                ", nome='" + nome + '\'' +
                '}';
    }
}