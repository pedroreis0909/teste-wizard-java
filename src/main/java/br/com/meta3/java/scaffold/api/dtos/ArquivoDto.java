package br.com.meta3.java.scaffold.api.dtos;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

/*
 TODO: (REVIEW) Renamed legacy 'codigoescola' to 'codigoEscola' and added @NotBlank for Jakarta validation
 // Legacy code: public void setCodigoescola(String codigoescola){ this.codigoescola = codigoescola; }
 The decision:
 - Use camelCase property name 'codigoEscola' to follow Java naming conventions and typical DTO patterns.
 - Apply @NotBlank at field level to ensure incoming API requests provide a non-empty value.
 - Provide standard getter/setter names (getCodigoEscola/setCodigoEscola) so Jackson/Spring binders work correctly.
*/
public class ArquivoDto {

    private Long id;
    private String nome;
    private String caminho;

    @NotBlank(message = "codigoEscola must not be blank")
    private String codigoEscola;

    public ArquivoDto() {
    }

    public ArquivoDto(Long id, String nome, String caminho, String codigoEscola) {
        this.id = id;
        this.nome = nome;
        this.caminho = caminho;
        this.codigoEscola = codigoEscola;
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

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getCodigoEscola() {
        return codigoEscola;
    }

    // Preserves legacy behavior of accepting a string and assigning to the DTO field.
    public void setCodigoEscola(String codigoEscola) {
        this.codigoEscola = codigoEscola;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArquivoDto)) return false;
        ArquivoDto that = (ArquivoDto) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(nome, that.nome) &&
               Objects.equals(caminho, that.caminho) &&
               Objects.equals(codigoEscola, that.codigoEscola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, caminho, codigoEscola);
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", caminho='" + caminho + '\'' +
                ", codigoEscola='" + codigoEscola + '\'' +
                '}';
    }
}