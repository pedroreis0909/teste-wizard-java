package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests to ensure legacy semdocumento setter behavior is preserved.
 *
 * Notes on decisions:
 * - The legacy code provided only the setter signature for semdocumento.
 * - To be resilient to variations in the Arquivo entity (presence/absence of a getter),
 *   the test first attempts to call a getter reflectively. If no getter exists,
 *   it falls back to reading the private field value reflectively.
 *
 * TODO comments are included to flag these migration/compatibility decisions for review.
 */
public class ArquivoEntityTest {

    @Test
    public void testSemdocumentoSetterGetterBehavior() throws Exception {
        Arquivo arquivo = new Arquivo();

        // Use the legacy setter directly (exists in legacy code).
        arquivo.setSemdocumento(123);

        // TODO: (REVIEW) Legacy code provided only a setter. Prefer calling getter if available,
        // but fall back to direct field inspection if the getter is absent.
        try {
            // Attempt to use a conventional getter if present
            Method getter = Arquivo.class.getMethod("getSemdocumento");
            Object value = getter.invoke(arquivo);
            assertNotNull(value, "getSemdocumento returned null");
            // Support both int and Integer return types via Number
            assertTrue(value instanceof Number, "getSemdocumento did not return a numeric type");
            assertEquals(123, ((Number) value).intValue());
        } catch (NoSuchMethodException e) {
            // TODO: (REVIEW) Falling back to reflective field access to preserve test robustness
            Field field = Arquivo.class.getDeclaredField("semdocumento");
            field.setAccessible(true);
            Object fieldValue = field.get(arquivo);
            assertNotNull(fieldValue, "semdocumento field is null after calling setter");
            assertTrue(fieldValue instanceof Number, "semdocumento field is not numeric");
            assertEquals(123, ((Number) fieldValue).intValue());
        }

        // Also assert that updating the value works as expected
        arquivo.setSemdocumento(0);

        try {
            Method getter = Arquivo.class.getMethod("getSemdocumento");
            Object value = getter.invoke(arquivo);
            assertNotNull(value, "getSemdocumento returned null after update");
            assertTrue(value instanceof Number, "getSemdocumento did not return a numeric type after update");
            assertEquals(0, ((Number) value).intValue());
        } catch (NoSuchMethodException e) {
            Field field = Arquivo.class.getDeclaredField("semdocumento");
            field.setAccessible(true);
            Object fieldValue = field.get(arquivo);
            assertNotNull(fieldValue, "semdocumento field is null after update");
            assertTrue(fieldValue instanceof Number, "semdocumento field is not numeric after update");
            assertEquals(0, ((Number) fieldValue).intValue());
        }
    }
}