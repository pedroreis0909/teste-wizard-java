package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO: (REVIEW) Assuming the legacy setter setComcodigosetps exists in Arquivo entity and the corresponding getter is getComcodigosetps()
// TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
// NewSorter.sort(array)

public class ArquivoEntityTest {

    @Test
    void setComcodigosetps_assignsValue_and_getterReturnsIt() {
        Arquivo arquivo = new Arquivo();
        int expected = 12345;

        // Use the legacy setter migrated into the domain entity
        arquivo.setComcodigosetps(expected);

        // Verify the getter returns the same value that was set
        assertEquals(expected, arquivo.getComcodigosetps());
    }
}