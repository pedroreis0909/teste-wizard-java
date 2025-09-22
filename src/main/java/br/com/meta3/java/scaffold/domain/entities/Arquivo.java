package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;

/*
 // TODO: (REVIEW) Using LOB mapping for content field
 // Arquivo.content is mapped as @Lob to support large binary/text data while preserving original behavior
*/
// TODO: (REVIEW) Using LOB mapping for content field
// Arquivo.content is mapped as @Lob to support large binary/text data while preserving original behavior

/*
 // TODO: (REVIEW) Using PrePersist to initialize createdAt
 // PrePersist is used instead of vendor-specific annotations to keep JPA portability and avoid extra dependencies
*/
// TODO: (REVIEW) Using PrePersist to initialize createdAt
// PrePersist is used instead of vendor-specific annotations to keep JPA portability and avoid extra dependencies

/*
 // TODO: (REVIEW) Using GenerationType.IDENTITY for id
 // Chosen to keep simple numeric primary key generation compatible with H2 and common RDBMS defaults
*/
// TODO: (REVIEW) Using GenerationType.IDENTITY for id
// Chosen to keep simple numeric primary key generation compatible with H2 and common RDBMS defaults

@Entity
@Table(name = "arquivos")
public class Arquivo {

    // preserve legacy public no-arg constructor
    public Arquivo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String name;

    // store the file/content as a large object (LOB) to support arbitrary size content
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private byte[] content;

    // store creation timestamp; set on persist to preserve original behavior of createdAt being assigned when saved
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // convenience constructor to create instances in one call (keeps legacy behavior plus useful API)
    public Arquivo(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    // equals and hashCode based on id to support entity identity semantics

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arquivo)) return false;
        Arquivo arquivo = (Arquivo) o;
        return Objects.equals(getId(), arquivo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}