package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

/**
 * Domain entity representing an Arquivo (file).
 * This class was updated to expose the legacy accessor for 'semdocumento'.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Lob
    @Column(name = "conteudo")
    private byte[] conteudo;

    // Adds legacy-backed field 'semdocumento' with JPA column mapping.
    @Column(name = "semdocumento", nullable = false)
    private int semdocumento;

    public Arquivo() {
    }

    public Arquivo(Long id, String nome, byte[] conteudo, int semdocumento) {
        this.id = id;
        this.nome = nome;
        this.conteudo = conteudo;
        // TODO: (REVIEW) Mapping semdocumento as primitive int to preserve legacy accessor semantics
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

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    // Preserve legacy accessor exactly as in LEGACY_CODE
    public int getSemdocumento(){
        return this.semdocumento;
    }

    public void setSemdocumento(int semdocumento){
        // TODO: (REVIEW) Mapping semdocumento as primitive int to preserve legacy accessor semantics
        this.semdocumento = semdocumento;
    }
}