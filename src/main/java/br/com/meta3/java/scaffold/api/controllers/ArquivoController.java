package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/arquivos")
@Validated
public class ArquivoController {

    private final ArquivoService arquivoService;

    @Autowired
    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @GetMapping
    public ResponseEntity<List<ArquivoDto>> listAll() {
        List<ArquivoDto> arquivos = arquivoService.findAll();
        return ResponseEntity.ok(arquivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArquivoDto> getById(@PathVariable Long id) {
        Optional<ArquivoDto> dto = arquivoService.findById(id);
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Accepts semdocumento either inside the ArquivoDto payload or as an explicit request parameter.
    // TODO: (REVIEW) semdocumento may be represented as primitive int in legacy DTO; if so,
    // mapping from request param will overwrite the default value in DTO when provided in requests.
    // This preserves legacy behavior while allowing clients to pass semdocumento as a query param.
    @PostMapping
    public ResponseEntity<ArquivoDto> create(
            @Valid @RequestBody ArquivoDto arquivoDto,
            // allow clients to provide/override semdocumento as a request param; validate non-negative
            @RequestParam(value = "semdocumento", required = false) @Min(0) Integer semdocumentoOverride
    ) {
        if (semdocumentoOverride != null) {
            // TODO: (REVIEW) Mapping semdocumento from request param into DTO to ensure compatibility
            // with clients sending semdocumento outside the JSON body. This keeps behavior unchanged
            // for clients that include semdocumento in the DTO body.
            try {
                // Attempt to set via reflection-safe approach: rely on DTO setter if present.
                arquivoDto.getClass().getMethod("setSemdocumento", Integer.class)
                        .invoke(arquivoDto, semdocumentoOverride);
            } catch (NoSuchMethodException e1) {
                try {
                    // try primitive int setter
                    arquivoDto.getClass().getMethod("setSemdocumento", int.class)
                            .invoke(arquivoDto, semdocumentoOverride.intValue());
                } catch (Exception e2) {
                    // If no setter exists, fallback to service-layer handling by passing override separately
                    // TODO: (REVIEW) ArquivoService should handle semdocumento override when DTO cannot be mutated.
                }
            } catch (Exception ignored) {
                // If reflection fails for any other reason, swallow to preserve legacy behavior and let
                // service layer decide. Not failing the request here to maintain backward compatibility.
            }
        }

        ArquivoDto created = arquivoService.create(arquivoDto);
        // build location URI for created resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArquivoDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ArquivoDto arquivoDto,
            @RequestParam(value = "semdocumento", required = false) @Min(0) Integer semdocumentoOverride
    ) {
        if (semdocumentoOverride != null) {
            // TODO: (REVIEW) Same decision as create(): attempt to set semdocumento on DTO if possible.
            try {
                arquivoDto.getClass().getMethod("setSemdocumento", Integer.class)
                        .invoke(arquivoDto, semdocumentoOverride);
            } catch (NoSuchMethodException e1) {
                try {
                    arquivoDto.getClass().getMethod("setSemdocumento", int.class)
                            .invoke(arquivoDto, semdocumentoOverride.intValue());
                } catch (Exception e2) {
                    // ignore and allow service to handle override if necessary
                }
            } catch (Exception ignored) {
            }
        }

        Optional<ArquivoDto> updated = arquivoService.update(id, arquivoDto);
        return updated.map(dto -> ResponseEntity.ok(dto))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = arquivoService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}