package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Arquivo entity to ensure legacy getter getComcodigosetps() behaves as expected.
 *
 * Notes:
 * - Some legacy entities may not expose setters for all fields. To preserve legacy behavior
 *   and keep tests robust, reflection is used to populate private fields when necessary.
 */
public class ArquivoEntityTest {

    @Test
    public void testGetComcodigosetps_returnsSetValue() throws Exception {
        Arquivo arquivo = new Arquivo();

        // TODO: (REVIEW) Using reflection to set private field comcodigosetps in Arquivo entity to preserve legacy behavior when setter is not present
        Field f = Arquivo.class.getDeclaredField("comcodigosetps");
        f.setAccessible(true);
        // set an expected legacy value
        f.set(arquivo, 123);

        // verify the legacy getter returns the value we populated
        assertEquals(123, arquivo.getComcodigosetps());
    }

    @Test
    public void testGetComcodigosetps_defaultValueIsZero() {
        Arquivo arquivo = new Arquivo();

        // TODO: (REVIEW) Assuming default primitive int initial value is 0, asserting legacy behavior
        int defaultValue = arquivo.getComcodigosetps();

        // primitive int fields default to 0; ensure legacy getter reflects that when not populated
        assertEquals(0, defaultValue, "Default value of comcodigosetps should be 0 when not initialized");
    }
}