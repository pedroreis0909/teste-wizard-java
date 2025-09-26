package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for application-level operations on Arquivo.
 * Responsible for mapping between ArquivoDto and Arquivo entity and
 * propagating fields (including comcodigosetps) through create/update flows.
 */
@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    @Transactional
    public ArquivoDto create(ArquivoDto dto) {
        Arquivo entity = toEntity(dto);

        // TODO: (REVIEW) Using ArquivoRepository (domain abstraction) to persist entities
        // ArquivoRepository.save(entity)
        Arquivo saved = arquivoRepository.save(entity);

        return toDto(saved);
    }

    @Transactional
    public ArquivoDto update(Long id, ArquivoDto dto) {
        Arquivo existing = arquivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Arquivo not found with id: " + id));

        // Map incoming dto fields to existing entity.
        // Keep mapping explicit to ensure comcodigosetps is propagated.
        if (dto.getNome() != null) {
            existing.setNome(dto.getNome());
        }

        // TODO: (REVIEW) Mapping comcodigosetps: legacy setter expects primitive int.
        // To avoid potential NPEs from nullable DTO, default to 0 when DTO value is null.
        // existing.setComcodigosetps(value)
        Integer dtoComcodigosetps = dto.getComcodigosetps();
        existing.setComcodigosetps(dtoComcodigosetps != null ? dtoComcodigosetps : 0);

        Arquivo saved = arquivoRepository.save(existing);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<ArquivoDto> findAll() {
        return arquivoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArquivoDto findById(Long id) {
        Arquivo entity = arquivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Arquivo not found with id: " + id));
        return toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!arquivoRepository.existsById(id)) {
            throw new IllegalArgumentException("Arquivo not found with id: " + id);
        }
        arquivoRepository.deleteById(id);
    }

    /**
     * Map DTO to Entity.
     * Ensures comcodigosetps from DTO is set on the entity.
     */
    private Arquivo toEntity(ArquivoDto dto) {
        Arquivo entity = new Arquivo();

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }

        entity.setNome(dto.getNome());

        // TODO: (REVIEW) Preserving legacy behavior: use primitive int in entity setter.
        // If DTO provides null for comcodigosetps, map to 0 to keep entity primitive semantics.
        // entity.setComcodigosetps(value)
        Integer dtoComcodigosetps = dto.getComcodigosetps();
        entity.setComcodigosetps(dtoComcodigosetps != null ? dtoComcodigosetps : 0);

        return entity;
    }

    /**
     * Map Entity to DTO.
     * Includes comcodigosetps so API responses return this field.
     */
    private ArquivoDto toDto(Arquivo entity) {
        ArquivoDto dto = new ArquivoDto();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());

        // TODO: (REVIEW) Expose comcodigosetps on DTO responses. Entity uses primitive int;
        // DTO exposes Integer to allow nullable clients if needed.
        // dto.setComcodigosetps(entity.getComcodigosetps())
        dto.setComcodigosetps(entity.getComcodigosetps());

        return dto;
    }
}