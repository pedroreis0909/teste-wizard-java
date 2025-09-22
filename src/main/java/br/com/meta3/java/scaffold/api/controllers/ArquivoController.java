package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * REST controller exposing CRUD operations for Arquivo.
 */
@RestController
@RequestMapping("/arquivos")
@Validated
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    // TODO: (REVIEW) Assuming ArquivoService#create returns ArquivoDto containing generated id
    // ArquivoDto saved = arquivoService.create(dto);

    /**
     * Create a new Arquivo.
     * Expects an ArquivoDto in the request body and returns the created resource with Location header.
     */
    @PostMapping
    public ResponseEntity<ArquivoDto> create(@Valid @RequestBody ArquivoDto dto) {
        // TODO: (REVIEW) Assuming ArquivoService#create persists and returns a DTO with id populated
        ArquivoDto saved = arquivoService.create(dto);

        // Build Location header to the newly created resource
        URI location = null;
        try {
            // TODO: (REVIEW) Assuming ArquivoDto has a getId() method returning a non-null identifier
            location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();
        } catch (Exception ex) {
            // If we cannot build location, still return created status with body
        }

        HttpHeaders headers = new HttpHeaders();
        if (location != null) {
            headers.setLocation(location);
        }

        return new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
    }

    /**
     * Retrieve all Arquivos.
     */
    @GetMapping
    public ResponseEntity<List<ArquivoDto>> findAll() {
        List<ArquivoDto> list = arquivoService.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Retrieve Arquivo by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArquivoDto> findById(@PathVariable("id") Long id) {
        // TODO: (REVIEW) Assuming ArquivoService#findById returns Optional<ArquivoDto> or null if not found
        Optional<ArquivoDto> maybe = Optional.ofNullable(arquivoService.findById(id));
        return maybe.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Arquivo not found"));
    }

    /**
     * Delete Arquivo by id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            // TODO: (REVIEW) Assuming ArquivoService#deleteById removes the entity or throws an exception if not found
            arquivoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            // service signals not found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Arquivo not found", ex);
        } catch (Exception ex) {
            // generic failure
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete Arquivo", ex);
        }
    }
}