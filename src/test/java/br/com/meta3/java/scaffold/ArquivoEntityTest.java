package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArquivoEntityTest {

    @Test
    public void testGetCodigoescolaReturnsValueWhenSet() {
        Arquivo arquivo = new Arquivo();
        String expected = "ESC12345";

        // TODO: (REVIEW) Some legacy entities might not provide a standard setter method
        // for the 'codigoescola' field (legacy code only showed the getter). To robustly
        // verify the legacy getter behavior was preserved after migration, we first try
        // to invoke a conventional setter (setCodigoescola). If it's not present we fall
        // back to using reflection to set the field directly.
        //
        // Using reflection here is a pragmatic choice to ensure the test verifies the
        // getter behavior regardless of whether a setter was generated/kept during migration.
        try {
            // Try to use a conventional setter if available
            Method setter = arquivo.getClass().getMethod("setCodigoescola", String.class);
            setter.invoke(arquivo, expected);
        } catch (NoSuchMethodException e) {
            // TODO: (REVIEW) Falling back to direct field access via reflection for legacy compatibility.
            try {
                Field field = arquivo.getClass().getDeclaredField("codigoescola");
                field.setAccessible(true);
                field.set(arquivo, expected);
            } catch (Exception ex) {
                // Re-throw as runtime to fail the test in case of unexpected reflection issues
                throw new RuntimeException("Failed to set codigoescola on Arquivo entity for test", ex);
            }
        } catch (Exception e) {
            // Re-throw as runtime to fail the test in case of unexpected invocation issues
            throw new RuntimeException("Failed to invoke setCodigoescola on Arquivo entity for test", e);
        }

        // Verify legacy getter returns the value we set (preserves original behavior)
        assertEquals(expected, arquivo.getCodigoescola());
    }
}