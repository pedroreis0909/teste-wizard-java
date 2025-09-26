package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    // TODO: (REVIEW) Delegating DTO-based API calls to ArquivoService to preserve legacy getter/setter behavior
    // arquivoService.create(arquivoDto)
    // NOTE: We accept and return ArquivoDto so that the legacy 'comerro' getter/setter present in domain/entity
    //       can flow through the service layer into the API layer without exposing internal entity classes.

    @GetMapping
    public ResponseEntity<List<ArquivoDto>> listAll() {
        List<ArquivoDto> dtos = arquivoService.findAll(); // expecting service to return DTOs for API layer
        return ResponseEntity.ok(dtos);
    }

    // TODO: (REVIEW) Ensuring comerro field is propagated through DTO <-> Domain mapping
    // ArquivoDto.getComerro();
    // NOTE: The controller forwards the ArquivoDto to the service so any legacy getComerro()/setComerro()
    //       implementations are exercised inside the application's mapping/logic.
    @GetMapping("/{id}")
    public ResponseEntity<ArquivoDto> getById(@PathVariable Long id) {
        ArquivoDto dto = arquivoService.findById(id);
        return ResponseEntity.of(Optional.ofNullable(dto));
    }

    @PostMapping
    public ResponseEntity<ArquivoDto> create(@Valid @RequestBody ArquivoDto arquivoDto) {
        ArquivoDto created = arquivoService.create(arquivoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArquivoDto> update(@PathVariable Long id, @Valid @RequestBody ArquivoDto arquivoDto) {
        ArquivoDto updated = arquivoService.update(id, arquivoDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        arquivoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}