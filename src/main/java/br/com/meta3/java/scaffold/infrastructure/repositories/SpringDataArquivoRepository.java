package br.com.meta3.java.scaffold.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;

/**
 * Spring Data binding for Arquivo persistence.
 *
 * By extending JpaRepository, Spring will provide the runtime implementation
 * for common CRUD operations. Extending the domain-level ArquivoRepository
 * ensures the repository contract used across the application is honored
 * and allows adding custom domain-specific repository methods there.
 */
// TODO: (REVIEW) Using SpringDataArquivoRepository to provide a Spring Data JPA implementation
// of the domain-level ArquivoRepository so legacy repository usages remain valid.
@Repository
public interface SpringDataArquivoRepository extends JpaRepository<Arquivo, Long>, ArquivoRepository {
    // TODO: (REVIEW) If ArquivoRepository declares custom methods that require JPQL/SQL or custom
    // implementations, provide those in ArquivoRepositoryImpl (in infrastructure.repositories) and
    // keep their signatures here. For simple derived queries, add method signatures below, for example:
    // List<Arquivo> findByNome(String nome);
}