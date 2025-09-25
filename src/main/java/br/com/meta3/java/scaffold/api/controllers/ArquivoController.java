package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    @Autowired
    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * Create a new Arquivo from the provided ArquivoDto.
     * The ArquivoDto is validated (@Valid) before being forwarded to the service layer.
     * The service is responsible for mapping/persisting the codigoEscola value.
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ArquivoDto arquivoDto) {
        // TODO: (REVIEW) Legacy code used a setter named setCodigoescola (lowercase 'e') which may not match DTO naming.
        // We assume ArquivoDto uses a canonical camelCase field (codigoEscola). Ensure service maps any legacy naming differences.
        Objects.requireNonNull(arquivoDto);

        // TODO: (REVIEW) Assuming ArquivoService exposes a save method that accepts ArquivoDto.
        // If the service API differs, adapt the call to the correct service method (e.g., create, persist, etc.).
        var result = arquivoService.save(arquivoDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * Update an existing Arquivo identified by id using the provided ArquivoDto.
     * ArquivoDto is validated (@Valid) and forwarded to the service for processing.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody ArquivoDto arquivoDto) {
        // TODO: (REVIEW) Ensure codigoEscola provided in ArquivoDto is processed by service and persisted on updates.
        Objects.requireNonNull(arquivoDto);

        // TODO: (REVIEW) Assuming ArquivoService exposes an update method taking (Long, ArquivoDto).
        // If the service API differs, adapt accordingly.
        var result = arquivoService.update(id, arquivoDto);

        return ResponseEntity.ok(result);
    }

    // Additional endpoints (e.g., GET/DELETE) could be added here following the same validation and forwarding pattern.
}