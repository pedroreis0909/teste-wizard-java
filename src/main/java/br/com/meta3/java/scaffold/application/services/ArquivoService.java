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
 *
 * This class focuses on mapping the anovigencia field between the domain entity and the API DTO,
 * preserving the legacy getter behavior (anovigencia represented as a raw String on the entity).
 */
@Service
@Transactional
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    /**
     * Create a new Arquivo from the provided DTO.
     */
    public ArquivoDto create(ArquivoDto dto) {
        Arquivo entity = toEntity(dto);

        // Persist entity
        Arquivo saved = arquivoRepository.save(entity);

        // Map back to DTO (ensuring anovigencia from entity.getAnovigencia() is returned)
        return toDto(saved);
    }

    /**
     * Update existing Arquivo identified by id with values from DTO.
     */
    public ArquivoDto update(Long id, ArquivoDto dto) {
        Arquivo existing = arquivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo not found with id: " + id));

        // TODO: (REVIEW) Preserving legacy behavior: anovigencia is treated as raw String and copied directly.
        // This avoids transformations that could change legacy semantics.
        existing.setAnovigencia(dto.getAnovigencia());

        // If there are other fields in DTO/entity they should be updated here as well.
        // TODO: (REVIEW) Only anovigencia mapping is enforced in this migration step to remain minimal and safe.
        Arquivo updated = arquivoRepository.save(existing);

        return toDto(updated);
    }

    /**
     * Retrieve Arquivo by id.
     */
    @Transactional(readOnly = true)
    public ArquivoDto getById(Long id) {
        Arquivo entity = arquivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo not found with id: " + id));

        // TODO: (REVIEW) Use the entity getter directly to preserve the legacy getter semantics.
        // The legacy getter simply returned the raw anovigencia string, so we keep that behavior.
        return toDto(entity);
    }

    /**
     * List all Arquivo entries.
     */
    @Transactional(readOnly = true)
    public List<ArquivoDto> listAll() {
        return arquivoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Map Entity to DTO, ensuring anovigencia is copied using entity.getAnovigencia() (legacy behavior).
     */
    private ArquivoDto toDto(Arquivo entity) {
        ArquivoDto dto = new ArquivoDto();

        // TODO: (REVIEW) Mapping anovigencia explicitly to ensure the legacy getter's returned value flows to the API.
        dto.setAnovigencia(entity.getAnovigencia());

        // TODO: (REVIEW) Map other common fields if they exist. We keep this minimal to avoid assumptions about DTO/entity shape.
        try {
            // Attempt to map id if present on both types without making assumptions about class details.
            // Many scaffolded DTOs/entities provide getId/setId, so map them when available.
            // This block is defensive: if methods don't exist, reflection exceptions are ignored.
            // NOTE: use of reflection kept minimal and non-intrusive — this is a best-effort mapping for id only.
            java.lang.reflect.Method getId = entity.getClass().getMethod("getId");
            Object id = getId.invoke(entity);
            java.lang.reflect.Method setId = dto.getClass().getMethod("setId", id != null ? id.getClass() : Object.class);
            if (id != null) {
                // Only call when a compatible setter exists
                setId.invoke(dto, id);
            }
        } catch (Exception ex) {
            // TODO: (REVIEW) Reflection failed or id not present — swallow to keep mapping resilient.
            // We don't fail the whole operation because id mapping is optional for this migration.
        }

        return dto;
    }

    /**
     * Map DTO to Entity, ensuring anovigencia from DTO is assigned to entity.anovigencia preserving legacy representation.
     */
    private Arquivo toEntity(ArquivoDto dto) {
        Arquivo entity = new Arquivo();

        // TODO: (REVIEW) Map anovigencia explicitly to maintain legacy getter/setter behavior on the entity.
        entity.setAnovigencia(dto.getAnovigencia());

        // TODO: (REVIEW) Minimal mapping approach: other fields should be mapped if and when required by future tasks.
        try {
            // Defensive mapping for id if present on DTO and entity.
            java.lang.reflect.Method getId = dto.getClass().getMethod("getId");
            Object id = getId.invoke(dto);
            if (id != null) {
                java.lang.reflect.Method setId = entity.getClass().getMethod("setId", id.getClass());
                setId.invoke(entity, id);
            }
        } catch (Exception ex) {
            // TODO: (REVIEW) Reflection failed or id not present — ignore to keep behavior stable.
        }

        return entity;
    }
}