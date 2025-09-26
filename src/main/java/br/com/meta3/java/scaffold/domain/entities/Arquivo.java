package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Domain entity representing an Arquivo (file).
 *
 * NOTE: This file was updated to include the legacy 'semdocumento' field and its
 * getter/setter to preserve legacy setter functionality and enable persistence/mapping.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tipo;

    private Long tamanho;

    // TODO: (REVIEW) Mapping legacy setter setSemdocumento(int) to entity field as primitive int to preserve legacy behavior (no nulls)
    // TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
    // NewSorter.sort(array)
    // Reasoning:
    // - The legacy setter accepts a primitive int; mapping to primitive keeps the same contract (no null allowed).
    // - We mark the column as nullable = false to reflect that contract at the DB level.
    @Column(name = "semdocumento", nullable = false)
    private int semdocumento;

    public Arquivo() {
        // default constructor for JPA
    }

    public Arquivo(String nome, String tipo, Long tamanho, int semdocumento) {
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.semdocumento = semdocumento;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    // Legacy setter preserved: public void setSemdocumento(int semdocumento)
    public void setSemdocumento(int semdocumento){
        this.semdocumento = semdocumento;
    }

    // Getter for semdocumento as requested to enable access and mapping
    public int getSemdocumento(){
        return this.semdocumento;
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tamanho=" + tamanho +
                ", semdocumento=" + semdocumento +
                '}';
    }
}