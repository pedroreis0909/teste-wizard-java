package br.com.meta3.java.scaffold.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;

/**
 * Repository abstraction (port) for the Arquivo aggregate.
 * By extending JpaRepository we provide standard CRUD operations and paging/sorting capabilities.
 *
 * Note: This interface represents the domain-facing contract. Spring Data will provide the runtime
 * implementation so application services can depend on this repository abstraction.
 */
@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

    // TODO: (REVIEW) Using Spring Data JpaRepository as repository abstraction for Arquivo domain
    // This decision couples the domain repository contract to Spring Data for convenience and reduced boilerplate.
    // If a stricter hexagonal architecture is desired, replace this with a plain interface (e.g., ArquivoRepositoryPort)
    // containing explicit CRUD method signatures and implement an adapter in infrastructure that delegates to Spring Data.

    // TODO: (REVIEW) Legacy code provided only an empty no-arg constructor for Arquivo.
    // Ensure the entity at domain/entities/Arquivo.java has a JPA-compatible no-arg constructor and proper annotations
    // (e.g., @Entity, @Id). If additional fields require custom queries, declare domain-specific finder methods here.
}