package br.com.meta3.java.scaffold.api.dtos;

import java.io.Serializable;

import jakarta.validation.constraints.PositiveOrZero;

/*
 // TODO: (REVIEW) Mapping legacy getQuantidaderegistro() to DTO field quantidadeRegistro
 // ArquivoDto.setQuantidadeRegistro(entity.getQuantidaderegistro());
 
 Explanation: The legacy code exposed a getter named getQuantidaderegistro() (lowercase 'r').
 To keep a clean and idiomatic API DTO we expose the property as 'quantidadeRegistro' (camelCase).
 The commented mapping line above is a reminder for whoever implements the entity->dto mapping
 to read from the legacy getter and set this DTO property accordingly.
*/

public class ArquivoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;

    // New property added to carry the quantidadeRegistro from the entity to API clients.
    // Validate that the value is not negative when provided.
    @PositiveOrZero(message = "quantidadeRegistro must be zero or a positive integer")
    private Integer quantidadeRegistro;

    public ArquivoDto() {
    }

    public ArquivoDto(Long id, String nome, Integer quantidadeRegistro) {
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

    public Integer getQuantidadeRegistro() {
        return quantidadeRegistro;
    }

    public void setQuantidadeRegistro(Integer quantidadeRegistro) {
        this.quantidadeRegistro = quantidadeRegistro;
    }

    @Override
    public String toString() {
        return "ArquivoDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", quantidadeRegistro=" + quantidadeRegistro +
                '}';
    }
}