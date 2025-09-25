package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * Controller responsible for exposing endpoints to create/update Arquivo entities.
 *
 * Notes about migration decisions are in TODO comments where assumptions were necessary
 * (e.g. assumed service method signatures or DTO/entity accessor names).
 */
@RestController
@RequestMapping("/api/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * Create a new Arquivo resource.
     *
     * - Accepts ArquivoDto with nomeArquivo (validated using jakarta.validation @Valid)
     * - Delegates to ArquivoService to create the domain entity
     * - Returns 201 Created with Location header pointing to the created resource
     *
     * Expected request JSON:
     * {
     *   "nomeArquivo": "some name"
     * }
     */
    @PostMapping
    public ResponseEntity<ArquivoDto> create(@Valid @RequestBody ArquivoDto arquivoDto,
                                             UriComponentsBuilder uriBuilder) {
        // TODO: (REVIEW) The legacy code provided a setter named setNomearquivo(String nomearquivo)
        // We assume the domain entity keeps that naming and the service layer exposes a create method
        // that accepts the raw nomeArquivo or the DTO. Here we call a create method on the service
        // and assume it returns the created Arquivo domain entity.
        Arquivo created = arquivoService.create(arquivoDto.getNomeArquivo());

        // TODO: (REVIEW) Mapping from domain entity to DTO:
        // We assume Arquivo has a getter getNomearquivo() matching the legacy setter naming,
        // and ArquivoDto has setNomeArquivo(...) to populate the response DTO.
        ArquivoDto responseDto = new ArquivoDto();
        responseDto.setNomeArquivo(created.getNomearquivo());

        // Build Location header using created id if available
        // TODO: (REVIEW) We assume Arquivo has a getId() method; if not, the Location header can be omitted or adjusted.
        URI location = null;
        try {
            if (created.getId() != null) {
                location = uriBuilder.path("/api/arquivos/{id}")
                        .buildAndExpand(created.getId())
                        .toUri();
            }
        } catch (Exception e) {
            // If getId or mapping fails for any reason, we still return CREATED without Location.
            location = null;
        }

        if (location != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(location);
            return new ResponseEntity<>(responseDto, headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        }
    }

    /**
     * Update the nomeArquivo of an existing Arquivo resource.
     *
     * - Accepts ArquivoDto with nomeArquivo (validated using jakarta.validation @Valid)
     * - Delegates to ArquivoService to perform the update
     * - Returns 204 No Content on success, 404 Not Found if entity does not exist
     *
     * Expected request JSON:
     * {
     *   "nomeArquivo": "updated name"
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
                                       @Valid @RequestBody ArquivoDto arquivoDto) {
        // TODO: (REVIEW) We assume the service exposes an update method that returns Optional<Arquivo>
        // with the updated entity when the id exists, or Optional.empty() when it does not.
        Optional<Arquivo> updated = arquivoService.update(id, arquivoDto.getNomeArquivo());

        if (updated.isPresent()) {
            // Successfully updated
            return ResponseEntity.noContent().build();
        } else {
            // Resource not found
            return ResponseEntity.notFound().build();
        }
    }

    // TODO: (REVIEW) Error handling and validation:
    // Rely on Spring Boot's default MethodArgumentNotValidException handling to return 400 Bad Request
    // when @Valid fails. If custom error payloads are required, implement @ControllerAdvice separately.
}