package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller exposing CRUD endpoints for Arquivo resources.
 * This controller accepts ArquivoDto instances (including the 'comerro' field)
 * and forwards them to the ArquivoService for processing.
 */
@RestController
@RequestMapping("/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    @Autowired
    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * Create a new Arquivo.
     * The request body is validated (@Valid) and forwarded to the service.
     */
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ArquivoDto arquivoDto) {
        // TODO: (REVIEW) Mapping legacy setComerro(int comerro) into DTO propagation to service
        arquivoService.create(arquivoDto);

        // TODO: (REVIEW) Using 204 No Content to indicate successful processing without exposing creation details
        return ResponseEntity.noContent().build();
    }

    /**
     * Update an existing Arquivo by id.
     * The request body is validated (@Valid) and forwarded to the service.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @Valid @RequestBody ArquivoDto arquivoDto) {
        // TODO: (REVIEW) Forwarding ArquivoDto including 'comerro' to ArquivoService for update
        arquivoService.update(id, arquivoDto);

        // TODO: (REVIEW) Returning 204 No Content after successful update
        return ResponseEntity.noContent().build();
    }
}