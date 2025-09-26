package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller exposing Arquivo endpoints.
 *
 * This controller binds request bodies to ArquivoDto and validates them using Jakarta Validation.
 * It returns ArquivoDto in responses so the HTTP API surface includes the 'comcodigosetps' field
 * carried by the DTO.
 */
@RestController
@RequestMapping("/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * Create a new Arquivo.
     * Request body is bound to ArquivoDto and validated (@Valid) - ensures 'comcodigosetps' is validated
     * according to annotations present on ArquivoDto.
     *
     * Returns created ArquivoDto with 201 Created and Location header pointing to the new resource.
     */
    @PostMapping
    public ResponseEntity<ArquivoDto> create(@Valid @RequestBody ArquivoDto arquivoDto) {
        // TODO: (REVIEW) Assuming ArquivoService.save accepts ArquivoDto and returns the persisted ArquivoDto.
        // We call the service layer with the DTO directly to preserve the DTO's 'comcodigosetps' field in the API layer.
        ArquivoDto created = arquivoService.save(arquivoDto);

        // TODO: (REVIEW) Build Location header pointing to newly created resource using its id.
        // If ArquivoDto uses a different id property name, adjust the path accordingly.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    /**
     * Get all Arquivos.
     * Returns DTOs so 'comcodigosetps' is included in responses.
     */
    @GetMapping
    public ResponseEntity<List<ArquivoDto>> getAll() {
        // TODO: (REVIEW) Assuming ArquivoService.findAll returns a List<ArquivoDto>.
        List<ArquivoDto> arquivos = arquivoService.findAll();
        return ResponseEntity.ok(arquivos);
    }

    /**
     * Get a single Arquivo by id.
     * Returns ArquivoDto including 'comcodigosetps'.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArquivoDto> getById(@PathVariable Long id) {
        // TODO: (REVIEW) Assuming ArquivoService.findById returns ArquivoDto or throws an exception handled elsewhere.
        ArquivoDto arquivo = arquivoService.findById(id);
        return ResponseEntity.ok(arquivo);
    }

    /**
     * Update an existing Arquivo.
     * Accepts ArquivoDto (with 'comcodigosetps') and validates the incoming payload.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArquivoDto> update(@PathVariable Long id, @Valid @RequestBody ArquivoDto arquivoDto) {
        // TODO: (REVIEW) Assuming ArquivoService.update accepts id + ArquivoDto and returns updated ArquivoDto.
        ArquivoDto updated = arquivoService.update(id, arquivoDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete an Arquivo by id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: (REVIEW) Assuming ArquivoService.deleteById exists and performs deletion.
        arquivoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}