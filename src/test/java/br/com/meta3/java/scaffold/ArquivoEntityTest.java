package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArquivoEntityTest {

    // TODO: (REVIEW) Using JUnit Jupiter (org.junit.jupiter.api) for tests instead of legacy JUnit 4
    // TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
    // NewSorter.sort(array)
    // Notes:
    // - The above TODOs document migration decisions: choosing JUnit 5 and keeping the test focused on the entity behavior.
    // - The example template line is included as a commented reference to follow the requested template without breaking compilation.

    @Test
    public void whenSetComerro_thenGetterReturnsValue() {
        // Arrange
        Arquivo arquivo = new Arquivo();

        // Act
        int expected = 42;
        arquivo.setComerro(expected);

        // Assert
        assertEquals(expected, arquivo.getComerro(), "getComerro should return the value previously set by setComerro");
    }
}