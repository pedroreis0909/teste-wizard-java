package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Arquivo operations.
 *
 * Migration notes and decisions are added as TODO comments where legacy behavior was adapted.
 */
@RestController
@RequestMapping("/api/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * Creates a new Arquivo from the provided DTO.
     *
     * - Accepts ArquivoDto with camelCase field nomeArquivo.
     * - The DTO preserves the external JSON field name "nomearquivo" via @JsonProperty on the DTO class.
     * - The endpoint parameter is validated with @Valid.
     * - Maps DTO.nomeArquivo -> Arquivo.setNomeArquivo(...) (updated camelCase setter).
     */
    @PostMapping
    public ResponseEntity<ArquivoDto> create(@RequestBody @Valid ArquivoDto arquivoDto) {
        // Map DTO to entity
        Arquivo arquivo = new Arquivo();

        // TODO: (REVIEW) The legacy setter was `setNomearquivo(String nomearquivo)` (lowercase 'a').
        // We intentionally map to the updated camelCase setter `setNomeArquivo` to follow JavaBean conventions.
        // This preserves newer entity naming while the DTO still exposes the legacy JSON property name.
        arquivo.setNomeArquivo(arquivoDto.getNomeArquivo());

        // TODO: (REVIEW) Calling arquivoService.save(...) as a placeholder for persistence.
        // Ensure ArquivoService exposes a compatible save/create method. If not present, implement it in
        // src/main/java/br/com/meta3/java/scaffold/application/services/ArquivoService.java
        Arquivo savedArquivo = arquivoService.save(arquivo);

        // Map entity back to DTO for response
        ArquivoDto responseDto = new ArquivoDto();
        responseDto.setNomeArquivo(savedArquivo.getNomeArquivo());

        // Return 201 Created with the persisted DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * Simple retrieval endpoint to demonstrate returning a DTO.
     * Accepts an id and returns the corresponding ArquivoDto if found.
     *
     * - Maps entity.nomeArquivo -> dto.nomeArquivo
     * - Preserves external JSON field name "nomearquivo" via DTO annotations.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArquivoDto> findById(@PathVariable Long id) {
        // TODO: (REVIEW) Using arquivoService.findById as a domain operation placeholder.
        // Ensure ArquivoService provides a method like Optional<Arquivo> findById(Long id).
        Arquivo arquivo = arquivoService.findById(id);

        if (arquivo == null) {
            return ResponseEntity.notFound().build();
        }

        ArquivoDto dto = new ArquivoDto();
        dto.setNomeArquivo(arquivo.getNomeArquivo());

        return ResponseEntity.ok(dto);
    }
}