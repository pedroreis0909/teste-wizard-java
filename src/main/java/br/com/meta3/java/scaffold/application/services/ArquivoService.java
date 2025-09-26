package br.com.meta3.java.scaffold.application.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;

@Service
@Transactional
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    @Autowired
    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    /**
     * Creates a new Arquivo from the provided DTO.
     * Ensures comerro from DTO is propagated to entity before saving.
     */
    public ArquivoDto create(ArquivoDto dto) {
        Arquivo entity = toEntity(dto);

        // TODO: (REVIEW) Mapping comerro field preserving legacy getter getComerro
        entity.setComerro(dto.getComerro());
        Arquivo saved = arquivoRepository.save(entity);

        return toDto(saved);
    }

    /**
     * Updates an existing Arquivo with values from the provided DTO.
     * Ensures comerro from DTO overwrites entity's comerro (preserve legacy behavior).
     */
    public ArquivoDto update(Long id, ArquivoDto dto) {
        Optional<Arquivo> optional = arquivoRepository.findById(id);
        Arquivo entity = optional.orElseThrow(() -> new IllegalArgumentException("Arquivo not found with id: " + id));

        // Map other updatable fields if present in DTO.
        // NOTE: Keep mapping simple; only fields known to exist should be mapped here.
        if (dto.getNome() != null) {
            entity.setNome(dto.getNome());
        }

        // TODO: (REVIEW) Mapping comerro on update to preserve legacy behavior
        entity.setComerro(dto.getComerro());

        Arquivo saved = arquivoRepository.save(entity);
        return toDto(saved);
    }

    /**
     * Finds an Arquivo by id and returns a populated DTO.
     * Ensures DTO.comerro is populated from entity.getComerro() (legacy getter semantics).
     */
    @Transactional(readOnly = true)
    public ArquivoDto findById(Long id) {
        Arquivo entity = arquivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Arquivo not found with id: " + id));

        // TODO: (REVIEW) Populating DTO comerro value using legacy getComerro
        return toDto(entity);
    }

    // -----------------
    // Mapping helpers
    // -----------------

    private Arquivo toEntity(ArquivoDto dto) {
        Arquivo entity = new Arquivo();

        // Id may be null for creation flows
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }

        entity.setNome(dto.getNome());

        // Handle comerro mapping carefully:
        // If DTO uses a nullable wrapper and is null, choose a sensible default (0) to avoid NPEs
        // TODO: (REVIEW) Defaulting null DTO.comerro to 0 to match legacy primitive int semantics
        Integer dtoComerro = dto.getComerro();
        entity.setComerro(dtoComerro != null ? dtoComerro : 0);

        return entity;
    }

    private ArquivoDto toDto(Arquivo entity) {
        ArquivoDto dto = new ArquivoDto();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());

        // Preserve legacy behavior: read comerro from entity (likely via legacy getter getComerro)
        // TODO: (REVIEW) Using entity.getComerro() to populate DTO.comerro following legacy accessor
        dto.setComerro(entity.getComerro());

        return dto;
    }
}