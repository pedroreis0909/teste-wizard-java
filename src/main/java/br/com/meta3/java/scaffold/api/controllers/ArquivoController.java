package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    @Autowired
    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * Returns all Arquivo resources as ArquivoDto.
     * Keeping contract stable so clients receive 'anovigencia' in the DTO.
     */
    @GetMapping
    public ResponseEntity<List<ArquivoDto>> listAll() {
        List<ArquivoDto> dtos = arquivoService.findAll();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Returns a single Arquivo by id as ArquivoDto.
     * Ensures response includes 'anovigencia' field from the migrated property.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArquivoDto> getById(@PathVariable Long id) {
        ArquivoDto dto = arquivoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Creates a new Arquivo from ArquivoDto.
     * Preserves legacy behavior for the 'anovigencia' property by forwarding it through the DTO.
     */
    @PostMapping
    public ResponseEntity<ArquivoDto> create(@Valid @RequestBody ArquivoDto dto) {
        // TODO: (REVIEW) Ensure legacy setter setAnovigencia(String) maps to DTO property 'anovigencia' and is forwarded to the service
        String anovigencia = dto.getAnovigencia();

        ArquivoDto created = arquivoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Updates an existing Arquivo identified by id using ArquivoDto.
     * Keeps API shape unchanged so clients can continue sending 'anovigencia'.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArquivoDto> update(@PathVariable Long id, @Valid @RequestBody ArquivoDto dto) {
        // TODO: (REVIEW) Forward 'anovigencia' through the DTO to preserve legacy behavior from setAnovigencia(String)
        String anovigencia = dto.getAnovigencia();

        ArquivoDto updated = arquivoService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes an Arquivo by id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        arquivoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}