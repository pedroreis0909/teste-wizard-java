package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * Domain entity representing Arquivo.
 * Kept lightweight and compatible with Spring Data JPA.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Example existing field; retained so entity remains useful in the domain.
    @Column(name = "nome")
    private String nome;

    // TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
    // NewSorter.sort(array)
    // Decision: anovigencia represents a year of validity. Using String to preserve legacy behavior
    // and avoid migration issues with existing persisted data. Added validation to enforce 4-digit year.
    @Column(name = "anovigencia", length = 4)
    @Size(min = 4, max = 4)
    @Pattern(regexp = "\\d{4}", message = "anovigencia must be a 4-digit year")
    private String anovigencia;

    public Arquivo() {
        // no-arg constructor required by JPA
    }

    public Arquivo(Long id, String nome, String anovigencia) {
        this.id = id;
        this.nome = nome;
        this.anovigencia = anovigencia;
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

    public String getAnovigencia() {
        return anovigencia;
    }

    // Legacy setter migrated as-is to preserve original behavior.
    public void setAnovigencia(String anovigencia){
        this.anovigencia = anovigencia;
    }

    // TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
    // NewSorter.sort(array)
    // Note: equals/hashCode/toString can be added later if entity comparisons or logs require them.
}