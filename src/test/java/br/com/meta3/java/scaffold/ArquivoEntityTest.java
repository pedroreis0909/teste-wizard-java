package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*
 // TODO: (REVIEW) Migrated legacy setter 'setNomearquivo' to 'setNomeArquivo' to follow JavaBean naming conventions
 Arquivo.class.getName();
 
 // TODO: (REVIEW) Tests below assume the entity enforces validation on 'nomeArquivo' (e.g., @NotNull / @NotBlank).
 // If validation annotations differ, update assertions accordingly.
 ValidatorFactory.class.getName();
*/

public class ArquivoEntityTest {

    @Test
    public void testGetterSetterNomeArquivo() {
        Arquivo arquivo = new Arquivo();

        // Verify we can set and get using the migrated camelCase accessor names
        arquivo.setNomeArquivo("documento.pdf");
        assertEquals("documento.pdf", arquivo.getNomeArquivo(), "getNomeArquivo should return the value set by setNomeArquivo");

        // Ensure changing value is reflected
        arquivo.setNomeArquivo("outro.txt");
        assertEquals("outro.txt", arquivo.getNomeArquivo(), "getNomeArquivo should return the updated value set by setNomeArquivo");
    }

    @Test
    public void testValidationNomeArquivo_constraints() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Validate a valid value - expect no constraint violations for the property 'nomeArquivo'
        Set<ConstraintViolation<Arquivo>> validViolations = validator.validateValue(Arquivo.class, "nomeArquivo", "arquivo_ok.txt");
        assertTrue(validViolations.isEmpty(), "Expected no validation violations for a valid nomeArquivo value");

        // Validate null value - if the entity enforces @NotNull/@NotBlank on nomeArquivo, expect violations
        Set<ConstraintViolation<Arquivo>> nullViolations = validator.validateValue(Arquivo.class, "nomeArquivo", null);
        assertFalse(nullViolations.isEmpty(), "Expected validation violations for null nomeArquivo (entity should enforce not-null/not-blank)");

        // Validate blank value - if the entity enforces @NotBlank on nomeArquivo, expect violations
        Set<ConstraintViolation<Arquivo>> blankViolations = validator.validateValue(Arquivo.class, "nomeArquivo", "");
        assertFalse(blankViolations.isEmpty(), "Expected validation violations for blank nomeArquivo (entity should enforce not-blank)");
    }
}