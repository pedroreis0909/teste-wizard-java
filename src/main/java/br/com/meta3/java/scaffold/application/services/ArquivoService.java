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
 * This class maps semdocumento between Arquivo and ArquivoDto for create/read/update flows,
 * validates the semdocumento value and persists changes via the ArquivoRepository.
 */
@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    /**
     * Create a new Arquivo from DTO, validating semdocumento and persisting.
     */
    @Transactional
    public ArquivoDto create(ArquivoDto dto) {
        validateSemdocumento(dto.getSemdocumento());

        // Map DTO -> entity (ensure semdocumento is set)
        Arquivo entity = toEntity(dto);

        Arquivo saved = arquivoRepository.save(entity);

        // Map persisted entity -> DTO (ensure semdocumento preserved)
        return toDto(saved);
    }

    /**
     * Find Arquivo by id and return as DTO.
     */
    @Transactional(readOnly = true)
    public ArquivoDto findById(Long id) {
        return arquivoRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Arquivo not found with id: " + id));
    }

    /**
     * Update existing Arquivo with values from DTO (including semdocumento) and persist.
     */
    @Transactional
    public ArquivoDto update(Long id, ArquivoDto dto) {
        Arquivo existing = arquivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Arquivo not found with id: " + id));

        validateSemdocumento(dto.getSemdocumento());

        // TODO: (REVIEW) Mapping semdocumento explicitly to preserve legacy behavior (legacy getter provided semdocumento)
        // TODO: (REVIEW) toEntity(dto)
        existing.setSemdocumento(dto.getSemdocumento());

        // NOTE: If there are additional fields in Arquivo/ArquivoDto they should be updated here as well.
        // TODO: (REVIEW) Only semdocumento guaranteed by legacy code; other fields left unchanged unless explicitly mapped
        // TODO: (REVIEW) existing.setOtherField(dto.getOtherField())

        Arquivo saved = arquivoRepository.save(existing);
        return toDto(saved);
    }

    /**
     * Delete an Arquivo by id.
     */
    @Transactional
    public void delete(Long id) {
        if (!arquivoRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Arquivo not found with id: " + id);
        }
        arquivoRepository.deleteById(id);
    }

    /**
     * List all Arquivo as DTOs.
     */
    @Transactional(readOnly = true)
    public List<ArquivoDto> listAll() {
        return arquivoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // -------------------------
    // Mapping helpers & validation
    // -------------------------

    private Arquivo toEntity(ArquivoDto dto) {
        Arquivo a = new Arquivo();
        if (dto.getId() != null) {
            a.setId(dto.getId());
        }

        // TODO: (REVIEW) Explicitly mapping semdocumento from DTO to Entity to reflect legacy getter usage
        // TODO: (REVIEW) toEntity(dto)
        a.setSemdocumento(dto.getSemdocumento());

        // NOTE: Other fields, if present, should be mapped here. They are intentionally omitted because
        // legacy provided only semdocumento snippet; mapping only the known field keeps behavior minimal and safe.
        // TODO: (REVIEW) a.setOtherField(dto.getOtherField())

        return a;
    }

    private ArquivoDto toDto(Arquivo a) {
        ArquivoDto dto = new ArquivoDto();
        dto.setId(a.getId());

        // TODO: (REVIEW) Explicitly mapping semdocumento from Entity to DTO to preserve legacy behavior
        // TODO: (REVIEW) toDto(a)
        dto.setSemdocumento(a.getSemdocumento());

        // NOTE: Map other entity fields to DTO here as needed.
        // TODO: (REVIEW) dto.setOtherField(a.getOtherField())

        return dto;
    }

    /**
     * Validate semdocumento value coming from DTO.
     * Business rule: semdocumento must not be null and must be non-negative.
     * Adjust validation rules here if project requires different constraints.
     */
    private void validateSemdocumento(Integer semdocumento) {
        // TODO: (REVIEW) Using non-null and non-negative validation because legacy code exposed primitive int getter (no null)
        // TODO: (REVIEW) validateSemdocumento(semdocumento)
        if (semdocumento == null) {
            throw new IllegalArgumentException("semdocumento must not be null");
        }
        if (semdocumento < 0) {
            throw new IllegalArgumentException("semdocumento must be non-negative");
        }
    }
}