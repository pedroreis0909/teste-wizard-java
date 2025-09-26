package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Arquivo related operations.
 *
 * This controller accepts and returns ArquivoDto instances.
 * Validation is applied to incoming requests via @Valid so fields
 * like 'aptos' declared in ArquivoDto are validated automatically.
 */
@RestController
@RequestMapping("/api/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * Create a new Arquivo
     *
     * The request body must be a valid ArquivoDto (including 'aptos').
     * The DTO is forwarded to the service layer for persistence/business logic.
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ArquivoDto arquivoDto) {
        // TODO: (REVIEW) Assuming ArquivoService#create accepts ArquivoDto.
        // If the service expects a domain entity, map ArquivoDto -> Arquivo there or adapt service signature.
        // Forwarding the DTO preserves the 'aptos' field provided by the client.
        Object created = arquivoService.create(arquivoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing Arquivo by id
     *
     * Accepts a valid ArquivoDto and forwards it to the service for update.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody ArquivoDto arquivoDto) {
        // TODO: (REVIEW) Assuming ArquivoService#update(Long, ArquivoDto) exists.
        // Decision: Forward full DTO including 'aptos' to allow updating that field.
        Object updated = arquivoService.update(id, arquivoDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Get an Arquivo by id
     *
     * The response should include 'aptos' if the service returns it.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        // TODO: (REVIEW) Assuming ArquivoService#findById(Long) returns a DTO or entity containing 'aptos'.
        Object found = arquivoService.findById(id);
        return ResponseEntity.ok(found);
    }

    /**
     * List all Arquivos
     *
     * Returns a list of Arquivo representations (DTOs/entities). Responses should contain 'aptos'.
     */
    @GetMapping
    public ResponseEntity<List<?>> listAll() {
        // TODO: (REVIEW) Assuming ArquivoService#findAll() returns List of DTOs/entities.
        List<?> list = arquivoService.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Delete an Arquivo by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        // TODO: (REVIEW) Assuming ArquivoService#delete(Long) exists and handles not-found conditions appropriately.
        arquivoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}