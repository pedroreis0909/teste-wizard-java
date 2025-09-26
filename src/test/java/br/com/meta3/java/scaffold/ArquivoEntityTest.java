package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArquivoEntityTest {

    @Test
    public void getAptos_shouldReturnValueWhenSet() {
        Arquivo arquivo = new Arquivo();

        // TODO: (REVIEW) We assume Arquivo exposes a standard setter setAptos(int) to mutate the field.
        // If the entity is immutable or uses a different builder/constructor, replace this line accordingly.
        arquivo.setAptos(5);

        // TODO: (REVIEW) We're asserting the legacy getter getAptos() returns the previously set value
        // This preserves the original behavior of the legacy getter in the migrated project.
        assertEquals(5, arquivo.getAptos(), "getAptos() should return the value previously set via setAptos()");
    }
}