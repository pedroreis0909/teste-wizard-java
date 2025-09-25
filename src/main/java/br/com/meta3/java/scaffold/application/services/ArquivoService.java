package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service responsible for application-level operations related to Arquivo.
 * This service maps between ArquivoDto and Arquivo entity and delegates persistence to ArquivoRepository.
 */
@Service
@Transactional
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    /**
     * Create a new Arquivo from DTO, persist it and return the saved DTO.
     */
    public ArquivoDto create(ArquivoDto dto) {
        Arquivo entity = toEntity(dto);

        // TODO: (REVIEW) Preserving legacy getter getCodigoescola mapping to entity field 'codigoescola'
        // entity.setCodigoescola(dto.getCodigoescola());
        Arquivo saved = arquivoRepository.save(entity);
        return toDto(saved);
    }

    /**
     * Update an existing Arquivo by id using values from DTO.
     * If entity is not found, Optional.empty() is returned.
     */
    public Optional<ArquivoDto> update(Long id, ArquivoDto dto) {
        Optional<Arquivo> existingOpt = arquivoRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return Optional.empty();
        }

        Arquivo existing = existingOpt.get();

        // Map updatable fields from dto to entity, including codigoescola to preserve legacy behavior.
        // TODO: (REVIEW) Ensuring codigoescola from DTO overwrites entity.codigoescola on update as legacy behavior expects
        // existing.setCodigoescola(dto.getCodigoescola());
        existing.setNome(dto.getNome());
        existing.setConteudo(dto.getConteudo());
        existing.setCodigoescola(dto.getCodigoescola());

        Arquivo updated = arquivoRepository.save(existing);
        return Optional.of(toDto(updated));
    }

    /**
     * Find Arquivo by id and map to DTO.
     */
    @Transactional(readOnly = true)
    public Optional<ArquivoDto> findById(Long id) {
        Optional<Arquivo> opt = arquivoRepository.findById(id);
        // TODO: (REVIEW) Mapping codigoescola from entity to DTO to preserve the legacy getter behavior
        // return opt.map(entity -> { dto.setCodigoescola(entity.getCodigoescola()); return dto; });
        return opt.map(this::toDto);
    }

    /**
     * List all Arquivo entities mapped to DTOs.
     */
    @Transactional(readOnly = true)
    public List<ArquivoDto> findAll() {
        return arquivoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Delete Arquivo by id.
     */
    public void delete(Long id) {
        arquivoRepository.deleteById(id);
    }

    /**
     * Map ArquivoDto to Arquivo entity.
     * Keep mapping explicit to ensure codigoescola is preserved across layers (legacy behavior).
     */
    private Arquivo toEntity(ArquivoDto dto) {
        if (dto == null) return null;
        Arquivo entity = new Arquivo();

        // If dto contains id, preserve it (useful for some operations)
        entity.setId(dto.getId());

        entity.setNome(dto.getNome());
        entity.setConteudo(dto.getConteudo());

        // TODO: (REVIEW) Explicitly mapping codigoescola from DTO to entity to maintain original behavior
        // entity.setCodigoescola(dto.getCodigoescola());
        entity.setCodigoescola(dto.getCodigoescola());

        return entity;
    }

    /**
     * Map Arquivo entity to ArquivoDto.
     * Ensure codigoescola is included in the DTO as the legacy getter exposed it.
     */
    private ArquivoDto toDto(Arquivo entity) {
        if (entity == null) return null;
        ArquivoDto dto = new ArquivoDto();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setConteudo(entity.getConteudo());

        // TODO: (REVIEW) Mapping entity.codigoescola into DTO.codigoescola to preserve legacy getter behavior
        // dto.setCodigoescola(entity.getCodigoescola());
        dto.setCodigoescola(entity.getCodigoescola());

        return dto;
    }
}