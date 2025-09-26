package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 * Domain entity representing an Arquivo.
 *
 * Migration notes:
 * - Introduced quantidadeRegistro field mapped to database column "quantidade_registro".
 * - Added modern camelCase getter getQuantidadeRegistro().
 * - Kept legacy getter getQuantidaderegistro() delegating to the modern getter to preserve behavior.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    // TODO: (REVIEW) Added quantidadeRegistro mapped to quantidade_registro DB column with @Column
    @Column(name = "quantidade_registro")
    private Integer quantidadeRegistro;

    public Arquivo() {
    }

    public Arquivo(Long id, String nome, Integer quantidadeRegistro) {
        this.id = id;
        this.nome = nome;
        this.quantidadeRegistro = quantidadeRegistro;
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
     * Modern camelCase getter for quantidadeRegistro.
     *
     * Returns the Integer wrapper type to allow presence of null (no value).
     */
    public Integer getQuantidadeRegistro() {
        return this.quantidadeRegistro;
    }

    public void setQuantidadeRegistro(Integer quantidadeRegistro) {
        this.quantidadeRegistro = quantidadeRegistro;
    }

    // TODO: (REVIEW) Delegating legacy getter getQuantidaderegistro() to modern getQuantidadeRegistro and handling null to avoid NPE
    getQuantidadeRegistro();
    /**
     * Legacy getter kept to preserve existing callers relying on the old method name.
     *
     * Original signature returned primitive int. To avoid NullPointerException when
     * quantidadeRegistro is null, we return 0 in that case (conservative default).
     *
     * This method delegates to the modern getter (getQuantidadeRegistro).
     */
    public int getQuantidaderegistro() {
        Integer v = getQuantidadeRegistro();
        return v == null ? 0 : v;
    }
}