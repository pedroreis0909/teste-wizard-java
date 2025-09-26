package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.infrastructure.repositories.SpringDataArquivoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ArquivoEntityTest {

    @Autowired
    private SpringDataArquivoRepository repository;

    // TODO: (REVIEW) Using reflection to set 'semdocumento' when setter is not available on Arquivo
    // reflectionField.setInt(entity, value)
    @Test
    void shouldReturnSemdocumento_afterConstruction() throws Exception {
        Arquivo arquivo = new Arquivo();

        // Try to set via setter if present, otherwise use reflection to set the field directly.
        int expected = 42;
        try {
            Method setter = Arquivo.class.getMethod("setSemdocumento", int.class);
            setter.invoke(arquivo, expected);
        } catch (NoSuchMethodException nsme) {
            // Setter not present; fallback to direct field access
            Field field = Arquivo.class.getDeclaredField("semdocumento");
            field.setAccessible(true);
            field.setInt(arquivo, expected);
        }

        // Verify the legacy getter is present and returns the expected value.
        assertThat(arquivo.getSemdocumento()).isEqualTo(expected);
    }

    // TODO: (REVIEW) Rely on Spring Data repository bean for persistence verification (ensures JPA mapping integrity)
    // repository.save(entity)
    @Test
    void shouldPersistAndRetrieveSemdocumento() throws Exception {
        Arquivo arquivo = new Arquivo();

        int expected = 7;
        // Set value using the same safe approach as above.
        try {
            Method setter = Arquivo.class.getMethod("setSemdocumento", int.class);
            setter.invoke(arquivo, expected);
        } catch (NoSuchMethodException nsme) {
            Field field = Arquivo.class.getDeclaredField("semdocumento");
            field.setAccessible(true);
            field.setInt(arquivo, expected);
        }

        // Persist entity
        repository.save(arquivo);

        // Retrieve persisted entity. We avoid assuming an ID getter; just fetch the first saved entity.
        Iterator<Arquivo> iterator = repository.findAll().iterator();
        assertThat(iterator.hasNext()).isTrue();
        Arquivo retrieved = iterator.next();

        // Assert legacy accessor still returns correct value after persistence
        assertThat(retrieved.getSemdocumento()).isEqualTo(expected);
    }
}