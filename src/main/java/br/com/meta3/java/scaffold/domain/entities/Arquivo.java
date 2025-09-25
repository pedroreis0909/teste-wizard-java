package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.util.Arrays;
import java.util.Objects;

/**
 * Domain entity representing a file (Arquivo).
 *
 * Note: This class was extended to include the legacy field 'codigoescola'.
 * The legacy getter getCodigoescola() is preserved to maintain backward compatibility
 * with any existing code that calls it. A conventional camelCase getter/setter
 * are also provided to integrate with frameworks and follow JavaBean conventions.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Example common file fields kept to integrate with other layers of the project.
    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "tamanho")
    private Long tamanho;

    @Lob
    @Column(name = "conteudo")
    private byte[] conteudo;

    // Legacy field requested to be added with JPA mapping.
    @Column(name = "codigoescola")
    private String codigoescola;

    public Arquivo() {
    }

    public Arquivo(Long id, String nome, String tipo, Long tamanho, byte[] conteudo, String codigoescola) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.conteudo = conteudo;
        this.codigoescola = codigoescola;
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

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    // TODO: (REVIEW) Preserve legacy getter name for backward compatibility
    public String getCodigoescola(){
        return this.codigoescola;
    }

    // TODO: (REVIEW) Added standard camelCase getter for framework/bean compatibility
    public String getCodigoEscola() {
        return this.codigoescola;
    }

    // TODO: (REVIEW) Added standard camelCase setter for framework/bean compatibility
    public void setCodigoEscola(String codigoEscola) {
        this.codigoescola = codigoEscola;
    }

    // Also provide a setter matching the legacy field name in case other code expects it.
    // TODO: (REVIEW) Provide legacy-style setter to maximize compatibility
    public void setCodigoescola(String codigoescola) {
        this.codigoescola = codigoescola;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arquivo arquivo = (Arquivo) o;
        return Objects.equals(id, arquivo.id)
            && Objects.equals(nome, arquivo.nome)
            && Objects.equals(tipo, arquivo.tipo)
            && Objects.equals(tamanho, arquivo.tamanho)
            && Arrays.equals(conteudo, arquivo.conteudo)
            && Objects.equals(codigoescola, arquivo.codigoescola);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, nome, tipo, tamanho, codigoescola);
        result = 31 * result + Arrays.hashCode(conteudo);
        return result;
    }

    @Override
    public String toString() {
        return "Arquivo{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", tipo='" + tipo + '\'' +
            ", tamanho=" + tamanho +
            ", conteudo=" + (conteudo != null ? ("[byte[" + conteudo.length + "]]") : "null") +
            ", codigoescola='" + codigoescola + '\'' +
            '}';
    }
}