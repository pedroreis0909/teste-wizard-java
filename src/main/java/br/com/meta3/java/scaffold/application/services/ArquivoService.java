package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Objects;

/**
 * Service responsible for application layer operations for Arquivo entities.
 * Handles mapping from ArquivoDto to Arquivo and performs basic validation.
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
     * @param dto the Arquivo DTO
     * @return the saved Arquivo entity
     */
    @Transactional
    public Arquivo create(ArquivoDto dto) {
        Objects.requireNonNull(dto, "ArquivoDto must not be null");

        Arquivo entity = new Arquivo();

        // Map fields from DTO to entity
        // TODO: (REVIEW) Basic mapping for other fields should be expanded as DTO evolves.
        // Example: entity.setNome(dto.getNome());

        // Map comerro with null-safety and basic validation
        // Legacy code: public void setComerro(int comerro){ this.comerro = comerro; }
        // We must accept nullable input from DTO (Integer) and validate before assigning.
        if (dto.getComerro() != null) {
            Integer comerro = dto.getComerro();
            validateComerro(comerro);
            // TODO: (REVIEW) Directly assigning DTO value to entity. If domain rules become complex,
            // consider using a dedicated mapper or factory to encapsulate invariants.
            entity.setComerro(comerro);
        } else {
            // TODO: (REVIEW) Decision: when DTO.comerro is null, we set a sensible default (0).
            // This mirrors safe behavior for primitive field in legacy code (int default 0) while
            // making the decision explicit. If business requires null persistence, change entity field to Integer.
            entity.setComerro(0);
        }

        return arquivoRepository.save(entity);
    }

    /**
     * Updates an existing Arquivo with values from the given DTO.
     *
     * @param id  the id of the Arquivo to update
     * @param dto the DTO containing updated values
     * @return the updated Arquivo entity
     */
    @Transactional
    public Arquivo update(Long id, ArquivoDto dto) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(dto, "ArquivoDto must not be null");

        Optional<Arquivo> opt = arquivoRepository.findById(id);
        Arquivo entity = opt.orElseThrow(() -> new NoSuchElementException("Arquivo not found with id: " + id));

        // Map fields from DTO to entity (only when provided)
        // TODO: (REVIEW) Partial update strategy chosen: only non-null DTO fields are applied.
        // If full-replace semantics are required, change logic to overwrite fields even when null.
        if (dto.getComerro() != null) {
            Integer comerro = dto.getComerro();
            validateComerro(comerro);
            entity.setComerro(comerro);
        }
        // else: keep existing value

        // TODO: (REVIEW) Map other updatable fields similarly:
        // if (dto.getNome() != null) entity.setNome(dto.getNome());

        return arquivoRepository.save(entity);
    }

    /**
     * Basic validation for comerro value.
     * Current rules: non-negative integer. Adjust as business rules evolve.
     *
     * @param comerro the value to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateComerro(Integer comerro) {
        // TODO: (REVIEW) Validation rule chosen: comerro must be >= 0.
        // If domain requires different constraints (max value, allowed set), update here.
        if (comerro < 0) {
            throw new IllegalArgumentException("comerro must be non-negative");
        }
    }
}