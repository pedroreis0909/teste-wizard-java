package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/arquivos")
@Validated
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @GetMapping
    public ResponseEntity<List<ArquivoDto>> listAll() {
        // TODO: (REVIEW) We assume arquivoService has a findAll() method returning List<Arquivo>.
        // If the service uses a different name/contract, adapt the call accordingly.
        List<Arquivo> arquivos = arquivoService.findAll();
        List<ArquivoDto> dtos = arquivos.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArquivoDto> getById(@PathVariable Long id) {
        // TODO: (REVIEW) We assume arquivoService.findById(id) returns an Optional-like semantics or null when not found.
        // If the service throws exceptions for not found, consider mapping exceptions to proper responses instead.
        Arquivo arquivo = arquivoService.findById(id);
        if (arquivo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityToDto(arquivo));
    }

    @PostMapping
    public ResponseEntity<ArquivoDto> create(@Valid @RequestBody ArquivoDto dto) {
        // Convert DTO (which includes codigoescola) to domain entity
        Arquivo toCreate = dtoToEntity(dto);

        // TODO: (REVIEW) We assume arquivoService.create or arquivoService.save exists and returns the persisted Arquivo.
        // Using 'create' as the method name; if the actual service exposes a different method, adjust accordingly.
        Arquivo created = arquivoService.create(toCreate);

        ArquivoDto createdDto = entityToDto(created);
        // Build location URI for created resource (id may be null depending on implementation)
        Long createdId = created.getId();
        if (createdId != null) {
            return ResponseEntity.created(URI.create("/arquivos/" + createdId)).body(createdDto);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArquivoDto> update(@PathVariable Long id, @Valid @RequestBody ArquivoDto dto) {
        // Convert DTO to entity and set id to ensure update semantics
        Arquivo toUpdate = dtoToEntity(dto);
        toUpdate.setId(id);

        // TODO: (REVIEW) We assume arquivoService.update(id, entity) or arquivoService.update(entity) exists.
        // Using arquivoService.update(entity) here; adjust if service contract differs.
        Arquivo updated = arquivoService.update(toUpdate);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityToDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: (REVIEW) We assume arquivoService.deleteById(id) exists and returns boolean or void.
        // Here we call deleteById and return 204. If the service returns info about deletion, adapt accordingly.
        arquivoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Mapping utilities between Arquivo (entity) and ArquivoDto (API contract)
    private ArquivoDto entityToDto(Arquivo arquivo) {
        if (arquivo == null) {
            return null;
        }
        ArquivoDto dto = new ArquivoDto();
        // TODO: (REVIEW) Mapping the legacy field 'codigoescola' explicitly so the API exposes it.
        // The legacy getter was getCodigoescola() — we map to DTO property with same name to preserve payload.
        dto.setId(arquivo.getId());
        dto.setNome(arquivo.getNome());
        dto.setConteudo(arquivo.getConteudo());
        dto.setCodigoescola(arquivo.getCodigoescola());
        // TODO: (REVIEW) If Arquivo/ArquivoDto use different property names, adjust mappings here.
        return dto;
    }

    private Arquivo dtoToEntity(ArquivoDto dto) {
        if (dto == null) {
            return null;
        }
        Arquivo arquivo = new Arquivo();
        arquivo.setId(dto.getId());
        arquivo.setNome(dto.getNome());
        arquivo.setConteudo(dto.getConteudo());
        // TODO: (REVIEW) Ensure setting the legacy field 'codigoescola' onto the entity so persistence contains it.
        // This preserves the legacy behavior where codigoescola is part of the domain model.
        arquivo.setCodigoescola(dto.getCodigoescola());
        return arquivo;
    }
}