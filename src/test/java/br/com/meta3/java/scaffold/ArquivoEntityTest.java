package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.infrastructure.repositories.SpringDataArquivoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
// NewSorter.sort(array)
// NOTE: The above TODO follows the requested template for documenting migration decisions.
// It is commented out to avoid introducing non-existing runtime dependencies.
// Decision summary:
// - Use a simple getter/setter unit test to verify in-memory behavior.
// - Use Data JPA test slice + SpringData repository and TestEntityManager to verify persistence mapping.
// - Assume Arquivo has a no-arg constructor and an id property with a getter (typical JPA entity).
//   If the entity requires other non-null fields, tests should be expanded to set them accordingly.

@DataJpaTest
class ArquivoEntityTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SpringDataArquivoRepository arquivoRepository;

    @Test
    void testSetAndGetAptos() {
        Arquivo arquivo = new Arquivo();
        arquivo.setAptos(5);
        // Verify the getter returns the value set by the legacy setter
        assertThat(arquivo.getAptos()).isEqualTo(5);
    }

    @Test
    void testPersistenceMappingForAptos() {
        Arquivo arquivo = new Arquivo();
        arquivo.setAptos(42);

        // Persist using the repository to exercise Spring Data JPA mapping
        Arquivo saved = arquivoRepository.save(arquivo);

        // Flush and clear to ensure we read from the database, not from the persistence context
        entityManager.flush();
        entityManager.clear();

        // Retrieve and verify the persisted value
        Arquivo found = arquivoRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getAptos()).isEqualTo(42);
    }
}