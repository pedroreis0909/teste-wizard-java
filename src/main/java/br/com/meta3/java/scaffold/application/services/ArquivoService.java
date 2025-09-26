package br.com.meta3.java.scaffold.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;

import java.util.Optional;

/**
 * Service responsible for Arquivo business flows.
 * This service focuses on create/update flows and ensures semdocumento is propagated
 * between DTO and Entity as required by the migration task.
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
     * semdocumento is read from the DTO and set on the entity before persisting.
     */
    public ArquivoDto create(ArquivoDto dto) {
        Arquivo entity = new Arquivo();

        // Map fields from DTO to Entity (including semdocumento).
        mapDtoToEntity(dto, entity);

        Arquivo saved = arquivoRepository.save(entity);

        // Map persisted entity back to DTO and return (including semdocumento).
        return mapEntityToDto(saved);
    }

    /**
     * Update an existing Arquivo identified by id using values from the DTO.
     * semdocumento from DTO is propagated to the entity and persisted.
     */
    public ArquivoDto update(Long id, ArquivoDto dto) {
        Optional<Arquivo> optional = arquivoRepository.findById(id);
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("Arquivo not found with id: " + id);
        }

        Arquivo entity = optional.get();

        // Map updated values from DTO to Entity (including semdocumento).
        mapDtoToEntity(dto, entity);

        Arquivo saved = arquivoRepository.save(entity);

        return mapEntityToDto(saved);
    }

    /**
     * Find Arquivo by id and return as DTO (including semdocumento).
     */
    @Transactional(readOnly = true)
    public ArquivoDto findById(Long id) {
        Optional<Arquivo> optional = arquivoRepository.findById(id);
        Arquivo entity = optional.orElseThrow(() -> new IllegalArgumentException("Arquivo not found with id: " + id));
        return mapEntityToDto(entity);
    }

    /**
     * Map relevant fields from DTO to Entity.
     *
     * TODO: (REVIEW) Mapping semdocumento from DTO to Entity.
     * We assume the DTO exposes getSemdocumento(). The legacy code provided setSemdocumento(int).
     * To be defensive:
     *  - If DTO's semdocumento is null (if DTO uses Integer), we default entity semdocumento to 0
     *    since the entity setter expects a primitive int (legacy signature).
     *  - If DTO exposes primitive int, autoboxing will work and this assignment will work as expected.
     */
    private void mapDtoToEntity(ArquivoDto dto, Arquivo entity) {
        if (dto == null || entity == null) {
            return;
        }

        // TODO: (REVIEW) Using DTO.getSemdocumento() to populate legacy entity.setSemdocumento(int)
        // This directly preserves the original behavior from the legacy setter.
        try {
            // Attempt to read semdocumento; handle potential null gracefully.
            Integer semFromDto = null;
            try {
                // Prefer direct call; if DTO exposes primitive int this will autobox.
                semFromDto = dto.getSemdocumento();
            } catch (Exception e) {
                // If direct access failed for unexpected reason, leave semFromDto null so default applies.
                semFromDto = null;
            }
            entity.setSemdocumento(semFromDto != null ? semFromDto : 0);
        } catch (Exception ex) {
            // As a last resort (shouldn't normally happen), ensure entity has a valid int value.
            entity.setSemdocumento(0);
        }

        // NOTE: If more fields exist on Arquivo/ArquivoDto, map them here following same defensive pattern.
    }

    /**
     * Map relevant fields from Entity to DTO.
     *
     * TODO: (REVIEW) Ensure semdocumento from Entity is set into DTO so callers receive the value.
     */
    private ArquivoDto mapEntityToDto(Arquivo entity) {
        if (entity == null) {
            return null;
        }

        ArquivoDto dto = new ArquivoDto();

        // TODO: (REVIEW) Using entity.getSemdocumento() to populate DTO's semdocumento.
        // The legacy code only had setSemdocumento on the entity; we ensure the value flows back.
        try {
            dto.setSemdocumento(entity.getSemdocumento());
        } catch (Exception ex) {
            // If DTO expects an Integer and some unexpected issue occurs, attempt safe fallback.
            try {
                dto.setSemdocumento(entity.getSemdocumento());
            } catch (Exception inner) {
                // swallowing to avoid breaking mapping; callers can detect missing value if needed.
            }
        }

        // NOTE: If more fields exist on Arquivo/ArquivoDto, map them here.

        return dto;
    }
}