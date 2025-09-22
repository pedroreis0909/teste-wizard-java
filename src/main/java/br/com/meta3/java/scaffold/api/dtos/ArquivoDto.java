package br.com.meta3.java.scaffold.api.dtos;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Transfer Object for Arquivo used by API layer.
 * Contains validation annotations (jakarta.validation) to validate incoming requests.
 */
public class ArquivoDto {

    // id is optional for create operations; when present it must be positive
    @Positive(message = "id must be positive")
    private Long id;

    @NotBlank(message = "nome must not be blank")
    @Size(max = 255, message = "nome must be at most 255 characters")
    private String nome;

    @NotBlank(message = "tipo must not be blank")
    @Size(max = 100, message = "tipo must be at most 100 characters")
    private String tipo;

    @NotBlank(message = "caminho must not be blank")
    @Size(max = 1024, message = "caminho must be at most 1024 characters")
    private String caminho;

    @NotNull(message = "tamanho must be provided")
    @PositiveOrZero(message = "tamanho must be zero or positive")
    private Long tamanho;

    @NotNull(message = "criadoEm must be provided")
    @PastOrPresent(message = "criadoEm must be in the past or present")
    private LocalDateTime criadoEm;

    public ArquivoDto() {
        // Default constructor required for frameworks (e.g., Jackson)
    }

    public ArquivoDto(Long id, String nome, String tipo, String caminho, Long tamanho, LocalDateTime criadoEm) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.caminho = caminho;
        this.tamanho = tamanho;
        this.criadoEm = criadoEm;
    }

    // TODO: (REVIEW) Mapping from DTO to domain entity assumes the domain entity 'Arquivo'
    // has a no-arg constructor and standard setters (setId, setNome, etc.). If the domain
    // entity is immutable or uses a different construction pattern, adapt this mapping.
    public Arquivo toDomain() {
        Arquivo entidade = new Arquivo();
        try {
            entidade.setId(this.id);
            entidade.setNome(this.nome);
            entidade.setTipo(this.tipo);
            entidade.setCaminho(this.caminho);
            entidade.setTamanho(this.tamanho);
            entidade.setCriadoEm(this.criadoEm);
        } catch (NoSuchMethodError | NoSuchMethodException | UnsupportedOperationException e) {
            // TODO: (REVIEW) If domain entity does not expose setters, replace this logic
            // with the appropriate constructor or builder usage. This catch is defensive;
            // in typical JPA entities setters are available.
            throw new IllegalStateException("Domain entity mapping failed due to incompatible API", e);
        }
        return entidade;
    }

    // TODO: (REVIEW) Mapping from domain entity to DTO assumes getters are present
    // (getId, getNome, etc.). If domain entity uses different method names, update accordingly.
    public static ArquivoDto fromDomain(Arquivo arquivo) {
        if (arquivo == null) {
            return null;
        }
        Long id = null;
        String nome = null;
        String tipo = null;
        String caminho = null;
        Long tamanho = null;
        LocalDateTime criadoEm = null;

        try {
            id = arquivo.getId();
            nome = arquivo.getNome();
            tipo = arquivo.getTipo();
            caminho = arquivo.getCaminho();
            tamanho = arquivo.getTamanho();
            criadoEm = arquivo.getCriadoEm();
        } catch (NoSuchMethodError | NoSuchMethodException e) {
            // TODO: (REVIEW) If domain entity does not expose getters, adapt mapping to available API.
            throw new IllegalStateException("Domain entity mapping failed due to incompatible API", e);
        }

        return new ArquivoDto(id, nome, tipo, caminho, tamanho, criadoEm);
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

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArquivoDto)) return false;
        ArquivoDto that = (ArquivoDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(tipo, that.tipo) &&
                Objects.equals(caminho, that.caminho) &&
                Objects.equals(tamanho, that.tamanho) &&
                Objects.equals(criadoEm, that.criadoEm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, tipo, caminho, tamanho, criadoEm);
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", caminho='" + caminho + '\'' +
                ", tamanho=" + tamanho +
                ", criadoEm=" + criadoEm +
                '}';
    }
}