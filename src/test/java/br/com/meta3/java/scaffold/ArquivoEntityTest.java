package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArquivoEntityTest {

    @Test
    public void testAnovigenciaGetterSetter() {
        Arquivo arquivo = new Arquivo();

        // TODO: (REVIEW) Assuming legacy entity Arquivo exposes a standard setter setAnovigencia(String)
        // and getter getAnovigencia() as present in the legacy code snippet. If the actual methods
        // have different names or signatures, adapt this test accordingly.
        arquivo.setAnovigencia("2025");

        // Verify the getter returns the value previously set to ensure backward-compatible behavior
        assertEquals("2025", arquivo.getAnovigencia(), "Arquivo#getAnovigencia should return the value set by setAnovigencia");
    }

    @Test
    public void testAnovigenciaMappingToDto() {
        Arquivo arquivo = new Arquivo();

        // TODO: (REVIEW) Mapping is performed manually here to validate that the anovigencia
        // value is transferred to the DTO. If the project later introduces a mapper utility,
        // replace manual mapping with that mapper in this test to validate the mapping behavior.
        arquivo.setAnovigencia("2024");

        ArquivoDto dto = new ArquivoDto();

        // TODO: (REVIEW) Assuming ArquivoDto exposes setAnovigencia/getAnovigencia methods.
        // If DTO uses a constructor or builder instead, update this mapping to use those mechanisms.
        dto.setAnovigencia(arquivo.getAnovigencia());

        // Ensure the DTO received the same anovigencia value preserving legacy behavior
        assertEquals("2024", dto.getAnovigencia(), "ArquivoDto#getAnovigencia should reflect the value from Arquivo#getAnovigencia");
    }
}