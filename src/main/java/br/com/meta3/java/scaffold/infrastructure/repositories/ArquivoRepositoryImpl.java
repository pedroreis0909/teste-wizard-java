package br.com.meta3.java.scaffold.infrastructure.repositories;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;

/*
 This implementation ensures any native SQL used here includes the 'comerro' column
 (either explicitly or via selecting all columns) so reads/writes map the field correctly.
 The class exposes a couple of helper methods that use native queries to read Arquivo
 entities and a simple save flow using the EntityManager to keep JPA lifecycle behavior.
*/

// TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
// Rationale: We use createNativeQuery(..., Arquivo.class) to let JPA/Hibernate map all columns
// including 'comerro' to the Arquivo entity. If later we need manual projection mapping,
// introduce an explicit mapping routine here.
@Repository
public class ArquivoRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ArquivoRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Finds an Arquivo by id using a native SQL query. The native SQL selects all columns
     * from the underlying table (SELECT a.*) so that the 'comerro' column is included
     * in the result set and properly mapped to the Arquivo entity.
     *
     * Using the entity mapping in createNativeQuery(..., Arquivo.class) delegates mapping
     * to JPA provider and ensures comerro is read.
     */
    @Transactional
    public Optional<Arquivo> findByIdNative(Long id) {
        String sql = "SELECT a.* FROM arquivo a WHERE a.id = :id";
        Query query = entityManager.createNativeQuery(sql, Arquivo.class);
        query.setParameter("id", id);
        try {
            Arquivo arquivo = (Arquivo) query.getSingleResult();
            return Optional.ofNullable(arquivo);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    /**
     * Returns all Arquivo rows using a native SQL query. Uses SELECT * to ensure any
     * present columns, including 'comerro', are returned and mapped to the entity.
     */
    @Transactional
    public List<Arquivo> findAllNative() {
        String sql = "SELECT a.* FROM arquivo a";
        Query query = entityManager.createNativeQuery(sql, Arquivo.class);
        @SuppressWarnings("unchecked")
        List<Arquivo> results = query.getResultList();
        return results;
    }

    /**
     * Saves or updates the Arquivo entity using standard JPA operations (persist/merge).
     * This ensures that the 'comerro' field (mapped on the entity) is persisted through
     * normal JPA lifecycle operations. If a project requirement arises that mandates
     * explicit native INSERT/UPDATE statements, implement them here making sure to
     * include the 'comerro' column in the SQL and parameters.
     */
    // TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
    // Decision: prefer JPA persist/merge to maintain entity state and cascading; use native
    // statements only when necessary and make sure to include 'comerro' in those statements.
    @Transactional
    public Arquivo save(Arquivo arquivo) {
        if (arquivo == null) {
            throw new IllegalArgumentException("arquivo must not be null");
        }

        if (getIdSafely(arquivo) == null) {
            entityManager.persist(arquivo);
            return arquivo;
        } else {
            return entityManager.merge(arquivo);
        }
    }

    // Helper to retrieve id reflectively without depending on a concrete getter name
    // This keeps the implementation resilient if the entity uses different id getter.
    // TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
    // Decision: simple reflective access limited to "getId" to avoid importing unknown types.
    private Long getIdSafely(Arquivo arquivo) {
        try {
            // Attempt to call getId() if present
            return (Long) Arquivo.class.getMethod("getId").invoke(arquivo);
        } catch (NoSuchMethodException nsme) {
            // No getId method; return null to treat as new
            return null;
        } catch (Exception ex) {
            // In case of any other reflection error, rethrow as runtime to flag the problem
            throw new RuntimeException("Unable to access id of Arquivo entity", ex);
        }
    }
}