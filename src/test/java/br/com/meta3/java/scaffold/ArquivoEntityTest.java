package br.com.meta3.java.scaffold;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: (REVIEW) Renamed field access from getNomearquivo/setNomearquivo to getNomeArquivo/setNomeArquivo
// NOTE: The legacy methods used snake-lower style (getNomearquivo). Tests updated to use camelCase (getNomeArquivo).
// TODO: (REVIEW) Added JSON mapping assertion to ensure property 'nomeArquivo' is serialized as expected
// The following line is a placeholder to indicate the decision above in the required template format.
// NewSorter.sort(array)
public class ArquivoEntityTest {

    @Test
    public void testGetSetNomeArquivo() {
        Arquivo arquivo = new Arquivo();

        // Use the new camelCase setter
        arquivo.setNomeArquivo("teste.txt");

        // Use the new camelCase getter
        String nome = arquivo.getNomeArquivo();

        assertEquals("teste.txt", nome, "Getter should return the value set by the camelCase setter");
    }

    @Test
    public void testJsonSerializationContainsNomeArquivo() throws Exception {
        Arquivo arquivo = new Arquivo();
        arquivo.setNomeArquivo("documento.pdf");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(arquivo);

        // Assert that the serialized JSON contains the camelCase property name.
        // This checks that JSON mapping (default or annotated) exposes 'nomeArquivo' as expected.
        assertTrue(json.contains("\"nomeArquivo\":\"documento.pdf\"") || json.contains("\"nomeArquivo\": \"documento.pdf\""),
                "Serialized JSON should contain the property 'nomeArquivo' with the expected value");

        // Also ensure we can deserialize back and retain the value via the camelCase property
        Arquivo deserialized = mapper.readValue(json, Arquivo.class);
        assertEquals("documento.pdf", deserialized.getNomeArquivo(), "Deserialized object should preserve nomeArquivo value");
    }
}