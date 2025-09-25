package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Service responsible for business operations related to Arquivo.
 * This class contains simple mapping logic between Arquivo and ArquivoDto,
 * including validation for the nomeArquivo field.
 */
@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    /**
     * Creates a new Arquivo from the given DTO.
     *
     * @param dto the ArquivoDto containing the data to create the entity
     * @return the created ArquivoDto
     */
    @Transactional
    public ArquivoDto create(ArquivoDto dto) {
        validateNomeArquivo(dto);

        Arquivo entity = toEntity(dto);

        // TODO: (REVIEW) Replaced legacy setter setNomearquivo with modern setNomeArquivo to follow camelCase convention
        // The legacy code used: entity.setNomearquivo(...)
        entity.setNomeArquivo(safeTrim(dto.getNomeArquivo()));

        Arquivo saved = arquivoRepository.save(entity);
        return toDto(saved);
    }

    /**
     * Updates an existing Arquivo identified by id using values from the DTO.
     *
     * @param id  the id of the Arquivo to update
     * @param dto the DTO with updated values
     * @return the updated ArquivoDto
     */
    @Transactional
    public ArquivoDto update(Long id, ArquivoDto dto) {
        validateNomeArquivo(dto);

        Optional<Arquivo> maybe = arquivoRepository.findById(id);
        Arquivo entity = maybe.orElseThrow(() -> new RuntimeException("Arquivo not found with id: " + id));

        // TODO: (REVIEW) Ensure mapping uses setNomeArquivo instead of legacy setNomearquivo to avoid compatibility issues
        // Replacing legacy call:
        // entity.setNomearquivo(dto.getNomeArquivo());
        entity.setNomeArquivo(safeTrim(dto.getNomeArquivo()));

        Arquivo updated = arquivoRepository.save(entity);
        return toDto(updated);
    }

    /**
     * Finds an Arquivo by id and returns its DTO representation.
     *
     * @param id the id to search
     * @return ArquivoDto if found
     */
    @Transactional(readOnly = true)
    public ArquivoDto findById(Long id) {
        Arquivo entity = arquivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo not found with id: " + id));
        return toDto(entity);
    }

    /**
     * Maps Arquivo entity to ArquivoDto.
     *
     * @param entity the Arquivo entity
     * @return mapped ArquivoDto
     */
    private ArquivoDto toDto(Arquivo entity) {
        if (entity == null) {
            return null;
        }
        ArquivoDto dto = new ArquivoDto();
        dto.setId(entity.getId());
        // Simple mapping for nomeArquivo field
        dto.setNomeArquivo(entity.getNomeArquivo());
        // Map other fields if present (keep simple to meet requirements)
        return dto;
    }

    /**
     * Maps ArquivoDto to Arquivo entity.
     *
     * @param dto the DTO to map
     * @return mapped Arquivo entity
     */
    private Arquivo toEntity(ArquivoDto dto) {
        if (dto == null) {
            return null;
        }
        Arquivo entity = new Arquivo();
        // NOTE: We intentionally do not set the id here for create operations.
        // Map nomeArquivo using the proper setter method.
        // TODO: (REVIEW) Using setNomeArquivo to replace legacy setNomearquivo call during mapping
        // entity.setNomearquivo(dto.getNomeArquivo());
        entity.setNomeArquivo(safeTrim(dto.getNomeArquivo()));
        return entity;
    }

    /**
     * Validates the nomeArquivo field on the provided DTO.
     *
     * @param dto the DTO to validate
     */
    private void validateNomeArquivo(ArquivoDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ArquivoDto must not be null");
        }
        String nome = dto.getNomeArquivo();
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("nomeArquivo must not be null or blank");
        }
        // Basic length validation to protect database constraints (common default)
        if (nome.trim().length() > 255) {
            throw new IllegalArgumentException("nomeArquivo must not exceed 255 characters");
        }
    }

    /**
     * Helper to safely trim strings and guard against nulls.
     *
     * @param value input value
     * @return trimmed string or null if input was null
     */
    private String safeTrim(String value) {
        return value == null ? null : value.trim();
    }
}