package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/**
 * Domain entity representing an Arquivo (file).
 * Modernized to follow JavaBean naming conventions and include JPA + Jakarta Validation annotations.
 */
@Entity
@Table(name = "arquivos")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: (REVIEW) Mapping nomeArquivo to DB column nome_arquivo with @Column to ensure snake_case DB naming
    // @Column(name = "nome_arquivo", nullable = false, length = 255)
    @Column(name = "nome_arquivo", nullable = false, length = 255)
    @NotBlank(message = "nomeArquivo must not be blank")
    @Size(max = 255, message = "nomeArquivo must be at most 255 characters")
    private String nomeArquivo;

    public Arquivo() {
    }

    public Arquivo(Long id, String nomeArquivo) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Standard JavaBean getter for nomeArquivo.
     */
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    // TODO: (REVIEW) Replaced legacy setter 'setNomearquivo' with 'setNomeArquivo' to follow JavaBean conventions
    // setNomeArquivo(String nomearquivo)
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    // Deprecated compatibility shim for any legacy code that may still call the old method name.
    // This preserves original functionality while signaling migration to the new method.
    // TODO: (REVIEW) Consider removing this deprecated shim after updating all references to use setNomeArquivo.
    @Deprecated
    public void setNomearquivo(String nomearquivo) {
        // delegate to the canonical setter
        setNomeArquivo(nomearquivo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arquivo)) return false;
        Arquivo arquivo = (Arquivo) o;
        return Objects.equals(id, arquivo.id) &&
               Objects.equals(nomeArquivo, arquivo.nomeArquivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeArquivo);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
               "id=" + id +
               ", nomeArquivo='" + nomeArquivo + '\'' +
               '}';
    }
}