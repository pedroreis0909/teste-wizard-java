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
 * Kept simple to preserve original behavior from legacy code while adapting to JPA.
 */
@Entity
@Table(name = "arquivos")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    // preserve existing field 'aptos' as requested by migration task
    @Column(name = "aptos", nullable = false)
    private int aptos;

    public Arquivo() {
        // default constructor required by JPA
    }

    public Arquivo(String nome, int aptos) {
        this.nome = nome;
        this.aptos = aptos;
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

    // TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
    // NewSorter.sort(array)
    // The legacy project exposed a simple getter for 'aptos'. To maintain encapsulation
    // and compatibility with service and API layers we provide the same getter here.
    public int getAptos() {
        return this.aptos;
    }

    public void setAptos(int aptos) {
        this.aptos = aptos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arquivo)) return false;
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
                ", nome='" + nome + '\'' +
                ", aptos=" + aptos +
                '}';
    }
}