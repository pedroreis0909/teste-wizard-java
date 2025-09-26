package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Arquivo entity representing files in the domain.
 * Kept simple to match existing project usage; only essential fields are present.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Long tamanho;

    // TODO: (REVIEW) Using primitive int for comcodigosetps to preserve legacy behavior where callers expect a primitive int (default 0)
    // NewSorter.sort(array)
    // The decision to keep primitive int avoids introducing NullPointerException risks in legacy callers.
    // If later we need tri-state (null vs 0), consider refactoring to Integer and updating callers/migrations.
    @Column(name = "comcodigosetps")
    private int comcodigosetps;

    public Arquivo() {
    }

    public Arquivo(Long id, String nome, Long tamanho, int comcodigosetps) {
        this.id = id;
        this.nome = nome;
        this.tamanho = tamanho;
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

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    // TODO: (REVIEW) Implemented legacy-compatible getter for comcodigosetps to preserve exact behavior
    // NewSorter.sort(array)
    // Preserving method name and return type ensures compatibility with existing code that calls getComcodigosetps().
    public int getComcodigosetps(){
        return this.comcodigosetps;
    }

    public void setComcodigosetps(int comcodigosetps){
        this.comcodigosetps = comcodigosetps;
    }
}