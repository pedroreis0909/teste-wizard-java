package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/arquivos")
@Validated
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    // TODO: (REVIEW) Mapping domain Arquivo to ArquivoDto in controller because legacy service returns domain entities.
    // We keep mapping here to ensure the controller always exposes the updated ArquivoDto (including aptos) to API clients.
    private ArquivoDto toDto(Arquivo arquivo) {
        if (arquivo == null) {
            return null;
        }
        ArquivoDto dto = new ArquivoDto();
        // TODO: (REVIEW) Setting fields explicitly to ensure aptos (legacy field) is exposed to API responses.
        dto.setId(arquivo.getId());
        dto.setNome(arquivo.getNome());
        dto.setAptos(arquivo.getAptos()); // Legacy getter preserved and exposed in DTO
        return dto;
    }

    // TODO: (REVIEW) Mapping ArquivoDto -> Arquivo entity in controller to call legacy service methods that expect entities.
    private Arquivo toEntity(ArquivoDto dto) {
        if (dto == null) {
            return null;
        }
        Arquivo entity = new Arquivo();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setAptos(dto.getAptos());
        return entity;
    }

    @GetMapping
    public ResponseEntity<List<ArquivoDto>> findAll() {
        // Assuming arquivoService.findAll() returns List<Arquivo>
        List<Arquivo> arquivos = arquivoService.findAll();
        List<ArquivoDto> dtos = arquivos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArquivoDto> findById(@PathVariable Long id) {
        // Assuming arquivoService.findById(id) returns Optional<Arquivo>
        Optional<Arquivo> optional = arquivoService.findById(id);
        return optional
                .map(arquivo -> ResponseEntity.ok(toDto(arquivo)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArquivoDto> create(@Valid @RequestBody ArquivoDto arquivoDto) {
        // Convert DTO to entity to persist via service
        Arquivo toSave = toEntity(arquivoDto);
        Arquivo saved = arquivoService.create(toSave);

        ArquivoDto resultDto = toDto(saved);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(resultDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArquivoDto> update(@PathVariable Long id, @Valid @RequestBody ArquivoDto arquivoDto) {
        // Ensure id consistency
        arquivoDto.setId(id);

        Arquivo toUpdate = toEntity(arquivoDto);
        Optional<Arquivo> updated = arquivoService.update(id, toUpdate);

        return updated
                .map(arquivo -> ResponseEntity.ok(toDto(arquivo)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = arquivoService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}