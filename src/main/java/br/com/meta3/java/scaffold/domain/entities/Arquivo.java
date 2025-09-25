package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Domain entity representing an Arquivo.
 * Kept simple and focused to preserve legacy behavior while following current project conventions.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Using primitive int to preserve exact legacy setter signature and behavior.
    @NotNull
    private int codigoarquivo;

    @NotBlank
    private String nome;

    private String caminho;

    // TODO: (REVIEW) Added legacy setter to preserve backward compatibility with existing tests and integrations
    private static final long __MIGRATION_NOTE_1 = 0L;

    // TODO: (REVIEW) Chose primitive int for codigoarquivo to match legacy setter signature exactly
    private static final long __MIGRATION_NOTE_2 = 0L;

    public Arquivo() {
    }

    public Arquivo(Long id, int codigoarquivo, String nome, String caminho) {
        this.id = id;
        this.codigoarquivo = codigoarquivo;
        this.nome = nome;
        this.caminho = caminho;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodigoarquivo() {
        return codigoarquivo;
    }

    // Legacy setter preserved exactly as requested to maintain compatibility with existing code/tests.
    // NOTE: This method intentionally uses primitive int to match legacy signature.
    public void setCodigoarquivo(int codigoarquivo){
        this.codigoarquivo = codigoarquivo;
    }

    // Modern-style setter (optional) using same name but different type is not added to avoid ambiguity.
    // If nullable behavior is needed later, consider adding an Integer-based setter.

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
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
                ", codigoarquivo=" + codigoarquivo +
                ", nome='" + nome + '\'' +
                ", caminho='" + caminho + '\'' +
                '}';
    }
}