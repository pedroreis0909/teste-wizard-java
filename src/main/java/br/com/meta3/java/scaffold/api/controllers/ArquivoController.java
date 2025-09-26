package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public ArquivoController(ArquivoService arquivoService, ObjectMapper objectMapper, Validator validator) {
        this.arquivoService = arquivoService;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    /**
     * Creates a new Arquivo resource.
     *
     * This endpoint accepts a JSON payload that maps to ArquivoDto. To preserve legacy behavior
     * where the record-quantity field was previously sent as "quantidaderegistro" (legacy lowercase
     * naming), we accept both "quantidadeRegistro" and "quantidaderegistro" keys. If the legacy
     * key is present, it will be mapped to the current DTO property name before conversion/validation.
     *
     * Validation: Uses jakarta.validation.Validator to programmatically validate the converted DTO
     * so we can accept legacy JSON keys while still leveraging @Valid-like semantics.
     *
     * TODO: (REVIEW) Mapping legacy JSON key "quantidaderegistro" to modern "quantidadeRegistro" to preserve legacy clients.
     */
    @PostMapping
    public ResponseEntity<?> createArquivo(@RequestBody Map<String, Object> payload) {
        // Normalize legacy field names to current DTO property names
        // TODO: (REVIEW) Handling legacy field "quantidaderegistro" to support old clients sending that property name.
        if (payload.containsKey("quantidaderegistro") && !payload.containsKey("quantidadeRegistro")) {
            payload.put("quantidadeRegistro", payload.get("quantidaderegistro"));
        }

        // Convert payload to ArquivoDto
        ArquivoDto arquivoDto = objectMapper.convertValue(payload, ArquivoDto.class);

        // Programmatically validate the DTO to emulate @Valid behavior (so we can accept legacy keys)
        Set<ConstraintViolation<ArquivoDto>> violations = validator.validate(arquivoDto);
        if (!violations.isEmpty()) {
            List<String> errors = violations.stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("errors", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Forward the validated DTO to the application service layer
        // TODO: (REVIEW) Forwarding DTO directly to ArquivoService to preserve legacy behavior for quantidadeRegistro.
        Object result = arquivoService.save(arquivoDto);

        // Return created/ok response. If service returns domain/entity or DTO, we forward it as-is.
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * Optional: Provide an endpoint to accept DTO directly with validation for clients using modern keys.
     *
     * This method keeps the @Valid-like behavior by validating programmatically, but allows clients
     * that already send the DTO using the modern "quantidadeRegistro" property to use a simpler endpoint.
     *
     * TODO: (REVIEW) Exposed lightweight endpoint accepting ArquivoDto semantics directly.
     */
    @PostMapping("/v2")
    public ResponseEntity<?> createArquivoV2(@RequestBody ArquivoDto arquivoDto) {
        // Programmatic validation to keep consistent behavior with createArquivo
        Set<ConstraintViolation<ArquivoDto>> violations = validator.validate(arquivoDto);
        if (!violations.isEmpty()) {
            List<String> errors = violations.stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("errors", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // TODO: (REVIEW) Ensure quantidadeRegistro is preserved and forwarded unchanged to service.
        Object result = arquivoService.save(arquivoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}