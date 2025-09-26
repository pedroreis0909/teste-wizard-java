package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Arquivo entity.
 *
 * This test adds assertions to ensure the legacy setter/getter for "anovigencia"
 * (migrated from legacy code) correctly stores and returns values on the entity instance.
 */
public class ArquivoEntityTest {

    // TODO: (REVIEW) Choosing unit test to validate setter/getter instead of persistence via JPA
    // Reason: Arquivo entity may contain additional non-null constraints or required fields that
    // are unrelated to the anovigencia property; keeping this as a simple unit test avoids DB coupling
    // and prevents flaky tests while still protecting the regression around the legacy setter/getter.
    // ArquivoEntityTest uses Arquivo#setAnovigencia to validate behavior
    // Arquivo#setAnovigencia("2024")

    @Test
    public void testSetAndGetAnovigencia_PersistsInEntityInstance() {
        Arquivo arquivo = new Arquivo();

        // Sanity: before setting, the value may be null (we don't assert strict initial state to avoid
        // depending on default initialization in the entity implementation).
        // Set the legacy property using the migrated setter.
        // TODO: (REVIEW) Using migrated legacy setter for anovigencia to ensure behavior parity with legacy code
        // arquivo.setAnovigencia("2024")
        String expectedAno = "2024";
        arquivo.setAnovigencia(expectedAno);

        // Verify the getter returns the expected value (ensures setter/getter work and the field is retained).
        assertEquals(expectedAno, arquivo.getAnovigencia(), "anovigencia should be retained in the Arquivo entity instance after setAnovigencia");
    }
}