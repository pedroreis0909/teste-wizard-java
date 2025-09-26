package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Arquivo entity focusing on comerro property.
 * Ensures legacy getter getComerro() behaves as expected and that
 * the setter (or reflection fallback) influences the getter result.
 */
public class ArquivoEntityTest {

    @Test
    void getComerro_DefaultIsZeroAndSetterInfluencesGetter() {
        Arquivo arquivo = new Arquivo();

        // Legacy behavior: default should be 0 (primitive int default)
        int defaultValue = arquivo.getComerro();
        assertEquals(0, defaultValue, "Expected default comerro to be 0");

        // Set value using setter if present, otherwise fallback to reflection.
        setComerroValue(arquivo, 7);
        assertEquals(7, arquivo.getComerro(), "getComerro() should return the value set by setter/reflection");

        // Change value again to ensure subsequent updates are reflected.
        setComerroValue(arquivo, -3);
        assertEquals(-3, arquivo.getComerro(), "getComerro() should reflect updated value");
    }

    @Test
    void getComerro_MultipleValues() {
        Arquivo arquivo = new Arquivo();

        setComerroValue(arquivo, 0);
        assertEquals(0, arquivo.getComerro());

        setComerroValue(arquivo, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, arquivo.getComerro());

        setComerroValue(arquivo, Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, arquivo.getComerro());
    }

    /**
     * Attempts to set comerro using public setter method setComerro(int).
     * If setter is not available or accessible, falls back to reflection to set the field directly.
     *
     * Complex decision: We prefer using the public setter to preserve encapsulation,
     * but to remain compatible with legacy code that may not expose a setter we fall back to reflection.
     */
    private void setComerroValue(Arquivo arquivo, int value) {
        try {
            Method setter = arquivo.getClass().getMethod("setComerro", int.class);
            setter.invoke(arquivo, value);
            return;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            // If setter is not available or invocation failed, we'll try reflection below.
        }

        // TODO: (REVIEW) Using reflection to set 'comerro' when setComerro is not present
        useReflectionToSetComerro(arquivo, value);
    }

    // Reflection helper separated to satisfy the TODO template and clearly mark the fallback approach.
    private void useReflectionToSetComerro(Arquivo arquivo, int value) {
        try {
            Field field = arquivo.getClass().getDeclaredField("comerro");
            field.setAccessible(true);
            field.setInt(arquivo, value);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            // Surface a clear failure in the test if we cannot set the value by any means.
            throw new RuntimeException("Unable to set 'comerro' on Arquivo entity via setter or reflection", ex);
        }
    }
}