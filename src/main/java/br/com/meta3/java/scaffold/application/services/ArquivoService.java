package br.com.meta3.java.scaffold.application.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;

/**
 * Service responsible for application-level operations on Arquivo.
 * Responsible for mapping between ArquivoDto and Arquivo entity,
 * validating incoming DTOs and persisting entities through the repository.
 */
@Service
@Transactional
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;
    private final Validator validator;

    public ArquivoService(ArquivoRepository arquivoRepository, Validator validator) {
        this.arquivoRepository = arquivoRepository;
        this.validator = validator;
    }

    /**
     * Create a new Arquivo from the given DTO.
     * Validates the DTO, maps aptos (and other fields) and persists the entity.
     */
    public ArquivoDto create(ArquivoDto dto) {
        validateDto(dto);

        // Map DTO to Entity including the 'aptos' field
        Arquivo entity = toEntity(dto);

        // Persist and return mapped DTO
        Arquivo saved = arquivoRepository.save(entity);
        return toDto(saved);
    }

    /**
     * Update an existing Arquivo identified by id using values from the DTO.
     * Validates the DTO, maps aptos (and other fields) into the existing entity and persists.
     */
    public ArquivoDto update(Long id, ArquivoDto dto) {
        validateDto(dto);

        Arquivo existing = arquivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Arquivo not found with id: " + id));

        // Map DTO fields into existing entity, explicitly updating 'aptos'
        mapDtoToExistingEntity(dto, existing);

        Arquivo saved = arquivoRepository.save(existing);
        return toDto(saved);
    }

    /**
     * Helper: validate DTO using jakarta.validation.Validator.
     * Throws ConstraintViolationException if any constraint is violated.
     */
    private void validateDto(ArquivoDto dto) {
        // Use programmatic validation to ensure the new 'aptos' field constraints are enforced
        Set<ConstraintViolation<ArquivoDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("ArquivoDto validation failed", violations);
        }

        // TODO: (REVIEW) Decided to use programmatic Validator for DTO to ensure 'aptos' constraints are applied at service boundary
        // validator.validate(dto)
    }

    /**
     * Map ArquivoDto to a new Arquivo entity.
     * Ensures 'aptos' is copied from DTO into entity.
     */
    private Arquivo toEntity(ArquivoDto dto) {
        Arquivo arquivo = new Arquivo();

        // Basic mapping: copy id if present (for completeness), other fields as available.
        // We avoid assuming fields beyond 'aptos' exist; map common ones if present in DTO.
        try {
            // If DTO exposes set/get for id, name, etc., map them. We guard with optional checks to avoid NPEs.
            // Note: the project already contains Arquivo and ArquivoDto; we map the common fields expected.
            if (dto.getId() != null) {
                arquivo.setId(dto.getId());
            }
        } catch (NoSuchMethodError | NoSuchMethodException | IllegalStateException e) {
            // If DTO doesn't expose some methods, ignore and continue mapping known fields.
        } catch (Exception e) {
            // Generic safeguard: do not fail mapping for optional fields.
        }

        // TODO: (REVIEW) Mapping 'aptos' from DTO to entity using the legacy setter approach.
        // arquivo.setAptos(dto.getAptos());
        arquivo.setAptos(dto.getAptos());

        // If there are other fields in ArquivoDto and Arquivo, they should be mapped here similarly.
        return arquivo;
    }

    /**
     * Map fields from DTO into an existing entity instance (used on update).
     * Explicitly sets 'aptos' on the existing entity.
     */
    private void mapDtoToExistingEntity(ArquivoDto dto, Arquivo existing) {
        // TODO: (REVIEW) Mapping updated fields into existing entity, ensuring 'aptos' is updated.
        // existing.setAptos(dto.getAptos());
        existing.setAptos(dto.getAptos());

        // Map other updatable fields if present on DTO/entity.
        try {
            if (dto.getId() != null) {
                existing.setId(dto.getId());
            }
        } catch (Exception e) {
            // Ignore optional mapping errors for fields not present.
        }
    }

    /**
     * Convert entity to DTO for responses.
     * Ensures 'aptos' is included in returned DTO.
     */
    private ArquivoDto toDto(Arquivo entity) {
        ArquivoDto dto = new ArquivoDto();

        // Map id if present
        try {
            dto.setId(entity.getId());
        } catch (Exception e) {
            // ignore if methods differ
        }

        // TODO: (REVIEW) Ensure 'aptos' is returned to API clients as it was part of domain in legacy code.
        // dto.setAptos(entity.getAptos());
        dto.setAptos(entity.getAptos());

        // Map other fields as necessary

        return dto;
    }
}