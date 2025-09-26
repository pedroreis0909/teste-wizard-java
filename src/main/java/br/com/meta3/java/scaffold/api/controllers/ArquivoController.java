package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * Create a new Arquivo.
     * Ensures comcodigosetps from the payload (ArquivoDto) is validated and passed to the service layer.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArquivoDto> create(@Valid @RequestBody ArquivoDto arquivoDto) {
        // TODO: (REVIEW) Ensure comcodigosetps is validated before delegating to service
        // TODO: (REVIEW) ArquivoDto.getComcodigosetps() must be provided by client and forwarded unchanged to the service
        // NOTE: we perform a minimal validation here to keep behavior predictable and to make sure
        // the legacy comcodigosetps value (previously accessed via entity getter) flows through the stack.
        if (arquivoDto == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // Basic validation for comcodigosetps to avoid passing invalid legacy values downstream.
            Integer comcodigosetps = null;
            try {
                // Using defensive access in case DTO uses primitive int or Integer
                comcodigosetps = arquivoDto.getComcodigosetps();
            } catch (Exception ex) {
                // If getter throws or is not present as expected, reject the request.
                return ResponseEntity.badRequest().build();
            }

            // If comcodigosetps is present, do a simple sanity check (non-negative).
            // TODO: (REVIEW) Validation rule: comcodigosetps must be non-negative integer
            if (comcodigosetps != null && comcodigosetps < 0) {
                return ResponseEntity.badRequest().build();
            }

            // Delegate to service passing the full DTO (which includes comcodigosetps).
            // TODO: (REVIEW) Passing ArquivoDto to ArquivoService.create to preserve legacy comcodigosetps flow
            // arquivoService.create(arquivoDto)
            ArquivoDto created = arquivoService.create(arquivoDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieve all Arquivos.
     * The returned DTOs should carry comcodigosetps in their payloads so clients can consume the legacy value.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArquivoDto>> findAll() {
        List<ArquivoDto> list = arquivoService.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Retrieve a single Arquivo by id.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArquivoDto> findById(@PathVariable Long id) {
        ArquivoDto dto = arquivoService.findById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    /**
     * Update an existing Arquivo.
     * Ensure comcodigosetps in the payload is forwarded to the service.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArquivoDto> update(@PathVariable Long id, @Valid @RequestBody ArquivoDto arquivoDto) {
        if (arquivoDto == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Integer comcodigosetps;
            try {
                comcodigosetps = arquivoDto.getComcodigosetps();
            } catch (Exception ex) {
                return ResponseEntity.badRequest().build();
            }

            if (comcodigosetps != null && comcodigosetps < 0) {
                return ResponseEntity.badRequest().build();
            }

            // TODO: (REVIEW) Ensure update forwards ArquivoDto (including comcodigosetps) to the service update method
            // arquivoService.update(id, arquivoDto)
            ArquivoDto updated = arquivoService.update(id, arquivoDto);
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete an Arquivo by id.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            boolean deleted = arquivoService.delete(id);
            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}