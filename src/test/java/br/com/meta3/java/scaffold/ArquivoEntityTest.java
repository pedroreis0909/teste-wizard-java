package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

// TODO: (REVIEW) Using NewSorter for LegacySorter in legacy code
// NewSorter.sort(array)
// TODO: (REVIEW) Using reflection to support legacy and new getter/setter names for Arquivo
// Reflection is used so tests can verify behavior whether the project has migrated to camelCase
// (setCodigoEscola/getCodigoEscola) or still exposes the legacy setCodigoescola method.
// This avoids compile-time coupling to a specific method name while ensuring backward compatibility is tested.

public class ArquivoEntityTest {

    private Method findMethod(Class<?> cls, String name, Class<?>... params) {
        try {
            return cls.getMethod(name, params);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Test
    public void testNewSetterAndGetterBehaviour() throws Exception {
        Arquivo arquivo = new Arquivo();

        // Prefer new-style names
        Method newSetter = findMethod(Arquivo.class, "setCodigoEscola", String.class);
        Method newGetter = findMethod(Arquivo.class, "getCodigoEscola");

        // Fallback to older naming if new-style not present
        if (newSetter == null) {
            newSetter = findMethod(Arquivo.class, "setCodigoescola", String.class);
        }
        if (newGetter == null) {
            newGetter = findMethod(Arquivo.class, "getCodigoescola");
        }

        assertNotNull(newSetter, "No setter found for codigo escola (expected setCodigoEscola or setCodigoescola)");
        assertNotNull(newGetter, "No getter found for codigo escola (expected getCodigoEscola or getCodigoescola)");

        String expected = "ESC123";
        newSetter.invoke(arquivo, expected);
        Object actual = newGetter.invoke(arquivo);

        assertEquals(expected, actual, "New setter/getter should store and return the provided codigo escola value");
    }

    @Test
    public void testLegacySetterDelegatesToNewImplementation() throws Exception {
        Arquivo arquivo = new Arquivo();

        // Legacy setter name (as in the legacy code)
        Method legacySetter = findMethod(Arquivo.class, "setCodigoescola", String.class);
        assertNotNull(legacySetter, "Legacy setter setCodigoescola must exist for backward compatibility");

        // Prefer new-style getter name, fallback to legacy getter
        Method newGetter = findMethod(Arquivo.class, "getCodigoEscola");
        if (newGetter == null) {
            newGetter = findMethod(Arquivo.class, "getCodigoescola");
        }
        assertNotNull(newGetter, "A getter for codigo escola must exist (getCodigoEscola or getCodigoescola)");

        // Use the legacy setter to assign a value
        String legacyValue = "LEGACY_ESC";
        legacySetter.invoke(arquivo, legacyValue);

        Object afterLegacy = newGetter.invoke(arquivo);
        assertEquals(legacyValue, afterLegacy, "Legacy setter should delegate to the current implementation so getter returns the legacy-set value");

        // Additionally verify that updating via new setter (if present) reflects for legacy getter as well
        Method newSetter = findMethod(Arquivo.class, "setCodigoEscola", String.class);
        Method legacyGetter = findMethod(Arquivo.class, "getCodigoescola");
        if (newSetter != null && legacyGetter != null) {
            String newValue = "NEW_ESC";
            newSetter.invoke(arquivo, newValue);
            Object viaLegacyGetter = legacyGetter.invoke(arquivo);
            assertEquals(newValue, viaLegacyGetter, "New setter should update the same underlying state readable by legacy getter");
        }
    }
}