package br.com.meta3.java.scaffold.infrastructure.repositories;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository implementation using the EntityManager.
 *
 * Migration notes:
 * - Legacy setter/getter names used setNomearquivo/getNomearquivo.
 *   All usages have been migrated to setNomeArquivo/getNomeArquivo to follow camelCase convention.
 * - Where native/native-like result mappings were required, the SQL aliases use camelCase
 *   (e.g. "nome_arquivo AS nomeArquivo") so manual mapping targets the entity's camelCase properties.
 *
 * Complex decisions or deviations are marked with TODO (REVIEW) comments as requested.
 */
@Repository
public class ArquivoRepositoryImpl implements ArquivoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Arquivo save(Arquivo arquivo) {
        // Using merge to support both insert and update flows.
        return entityManager.merge(arquivo);
    }

    @Override
    public Optional<Arquivo> findById(Long id) {
        Arquivo arquivo = entityManager.find(Arquivo.class, id);
        return Optional.ofNullable(arquivo);
    }

    @Override
    public List<Arquivo> findAll() {
        /*
         * We use a native query with explicit aliases that map to the camelCase entity fields.
         * This keeps manual mapping predictable and consistent with JSON/DB mapping annotations
         * that should be present on the Arquivo entity (e.g. @Column(name = "nome_arquivo")).
         *
         * NOTE: Table name "arquivo" is used as the legacy DB table. If your actual table differs,
         * adjust the FROM clause accordingly.
         */
        String sql = "SELECT a.id AS id, a.nome_arquivo AS nomeArquivo FROM arquivo a";
        Query q = entityManager.createNativeQuery(sql);
        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        List<Arquivo> arquivos = new ArrayList<>(rows.size());
        for (Object[] row : rows) {
            Arquivo arquivo = mapRowToArquivo(row);

            // TODO: (REVIEW) Replace legacy setNomearquivo/getNomearquivo with setNomeArquivo/getNomeArquivo
            arquivo.setNomeArquivo(arquivo.getNomeArquivo());

            arquivos.add(arquivo);
        }
        return arquivos;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Arquivo arquivo = entityManager.find(Arquivo.class, id);
        if (arquivo != null) {
            entityManager.remove(arquivo);
        }
    }

    @Override
    public Optional<Arquivo> findByNomeArquivo(String nomeArquivo) {
        // Use JPQL to avoid native SQL differences and to leverage entity mapping.
        List<Arquivo> results = entityManager.createQuery(
                        "SELECT a FROM Arquivo a WHERE a.nomeArquivo = :nome", Arquivo.class)
                .setParameter("nome", nomeArquivo)
                .getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(results.get(0));
    }

    /**
     * Map a native query row (Object[]) into an Arquivo entity instance.
     * The indices assume the native SELECT used in findAll():
     *   0 -> id (Number)
     *   1 -> nomeArquivo (String)
     *
     * All manual assignments must use the entity's camelCase setters (e.g. setNomeArquivo).
     */
    private Arquivo mapRowToArquivo(Object[] row) {
        Arquivo arquivo = new Arquivo();

        if (row.length > 0 && row[0] != null) {
            // id column
            Number idNum = (Number) row[0];
            // TODO: (REVIEW) Using Long conversion for numeric id mapping
            arquivo.setId(idNum.longValue());
        }

        if (row.length > 1 && row[1] != null) {
            String nomeArquivo = row[1].toString();

            // TODO: (REVIEW) Replace legacy setNomearquivo/getNomearquivo with setNomeArquivo/getNomeArquivo
            // The legacy code called setNomearquivo(...). We now use setNomeArquivo(...) to align with
            // Java camelCase conventions and the Arquivo entity's property names.
            arquivo.setNomeArquivo(nomeArquivo);
        }

        // If additional columns are added to the SELECT, map them here ensuring camelCase setter usage
        // and alignment with @Column(name = "...") declarations on the Arquivo entity.
        //
        // TODO: (REVIEW) Manual mapping chosen to avoid depending on automatic SqlResultSetMapping.
        // Manual mapping also allows us to rename/alias DB columns (snake_case) to entity fields (camelCase).
        // mapRowToArquivo should be extended if more columns are required.

        return arquivo;
    }
}