package br.com.meta3.java.scaffold.application.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;

/**
 * Service layer responsible for business operations related to Arquivo.
 * Provides mapping between domain entity and DTO while preserving legacy field
 * comcodigosetps across service boundaries.
 */
@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    /**
     * Convert domain entity to DTO, ensuring comcodigosetps is copied to preserve legacy behavior.
     */
    public ArquivoDto toDto(Arquivo entity) {
        if (entity == null) {
            return null;
        }

        ArquivoDto dto = new ArquivoDto();

        // Map common fields if present. Because the project structure does not
        // define a fixed set of fields here, we defensively map typical ones.
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setConteudo(entity.getConteudo());
        dto.setTamanho(entity.getTamanho());

        // TODO: (REVIEW) Preserve legacy field comcodigosetps mapping between entity and DTO
        // Using explicit copy to avoid losing the legacy value when passing through service boundaries
        // This mirrors the legacy getter getComcodigosetps() behavior by ensuring the value is propagated.
        dto.setComcodigosetps(entity.getComcodigosetps());

        return dto;
    }

    /**
     * Convert DTO to domain entity, ensuring comcodigosetps is copied back so persistence retains legacy value.
     */
    public Arquivo toEntity(ArquivoDto dto) {
        if (dto == null) {
            return null;
        }

        Arquivo entity = new Arquivo();

        // If the DTO contains an id, set it so updates are handled correctly.
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setConteudo(dto.getConteudo());
        entity.setTamanho(dto.getTamanho());

        // TODO: (REVIEW) Ensure comcodigosetps is preserved when converting DTO->Entity
        // Explicitly set the legacy field to maintain business semantics originating from legacy code.
        entity.setComcodigosetps(dto.getComcodigosetps());

        return entity;
    }

    /**
     * Save or update an Arquivo using DTO. Preserves comcodigosetps through the conversion.
     */
    public ArquivoDto save(ArquivoDto dto) {
        Arquivo entity = toEntity(dto);
        Arquivo saved = arquivoRepository.save(entity);
        return toDto(saved);
    }

    /**
     * Find by id and return DTO representation.
     */
    public Optional<ArquivoDto> findById(Long id) {
        return arquivoRepository.findById(id).map(this::toDto);
    }

    /**
     * Delete an Arquivo by id.
     */
    public void deleteById(Long id) {
        arquivoRepository.deleteById(id);
    }

    /**
     * List all Arquivos as DTOs.
     */
    public List<ArquivoDto> findAll() {
        return arquivoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}