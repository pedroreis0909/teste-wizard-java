package br.com.meta3.java.scaffold;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
        // sanity check to ensure Spring context starts
    }

    @Test
    void arquivoGetSetNomeArquivo() {
        // TODO: (REVIEW) Replaced legacy setNomearquivo/getNomearquivo with setNomeArquivo/getNomeArquivo
        Arquivo arquivo = new Arquivo();

        // TODO: (REVIEW) If Jackson/Jakarta Validation annotations were added on Arquivo,
        // we keep this test simple to avoid triggering validation on plain POJO usage.
        // Arquivo instantiation here is intended solely to verify the renamed accessors.
        arquivo.setNomeArquivo("documento.pdf");

        // Verify getter reflects the value set via the new setter name
        assertEquals("documento.pdf", arquivo.getNomeArquivo());
    }
}