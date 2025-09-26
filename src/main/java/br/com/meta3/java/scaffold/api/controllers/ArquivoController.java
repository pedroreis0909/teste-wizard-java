package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for exposing Arquivo endpoints.
 * Ensures ArquivoDto (including semdocumento) is accepted and forwarded to ArquivoService.
 */
@RestController
@RequestMapping("/api/arquivos")
public class ArquivoController {

    private static final Logger logger = LoggerFactory.getLogger(ArquivoController.class);

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    // TODO: (REVIEW) Forwarding ArquivoDto from controller to service layer to preserve semdocumento field handling
    // ArquivoService.create(arquivoDto)

    /**
     * Create a new Arquivo.
     * Accepts ArquivoDto which must contain semdocumento (and other fields as defined in ArquivoDto).
     */
    @PostMapping
    public ResponseEntity<ArquivoDto> create(@Valid @RequestBody ArquivoDto arquivoDto) {
        logger.debug("Received request to create Arquivo: {}", arquivoDto);
        // TODO: (REVIEW) Assuming application service exposes a create(ArquivoDto) method that handles mapping/validation/persistence.
        // arquivoService.create(arquivoDto)
        ArquivoDto created = arquivoService.create(arquivoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // TODO: (REVIEW) Exposing update endpoint that forwards ArquivoDto including semdocumento to the service layer
    // arquivoService.update(id, arquivoDto)

    /**
     * Update an existing Arquivo by id.
     * Forwards the ArquivoDto (including semdocumento) to the service for processing.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArquivoDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody ArquivoDto arquivoDto) {
        logger.debug("Received request to update Arquivo id={} payload={}", id, arquivoDto);
        ArquivoDto updated = arquivoService.update(id, arquivoDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Simple endpoint to retrieve an Arquivo by id.
     * Included to provide a round-trip for created/updated resources.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArquivoDto> getById(@PathVariable("id") Long id) {
        logger.debug("Received request to get Arquivo by id={}", id);
        ArquivoDto dto = arquivoService.findById(id);
        return ResponseEntity.ok(dto);
    }
}