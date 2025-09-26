package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service responsible for application business logic around Arquivo.
 * This service maps between Arquivo (entity) and ArquivoDto (API layer).
 *
 * Migration note:
 * We ensure the 'aptos' field is explicitly preserved across boundaries (DTO <-> Entity)
 * as requested by the migration task.
 */
@Service
@Transactional
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    @Autowired
    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    public ArquivoDto create(ArquivoDto dto) {
        Objects.requireNonNull(dto, "ArquivoDto must not be null");

        Arquivo entity = toEntity(dto);
        Arquivo saved = arquivoRepository.save(entity);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public Optional<ArquivoDto> findById(Long id) {
        return arquivoRepository.findById(id).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<ArquivoDto> findAll() {
        return arquivoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ArquivoDto> update(Long id, ArquivoDto dto) {
        Objects.requireNonNull(dto, "ArquivoDto must not be null");
        return arquivoRepository.findById(id).map(existing -> {
            // copy common properties from DTO to entity
            BeanUtils.copyProperties(dto, existing, "id");
            // TODO: (REVIEW) Ensure aptos is preserved when updating from DTO to entity
            existing.setAptos(dto.getAptos());
            Arquivo updated = arquivoRepository.save(existing);
            return toDto(updated);
        });
    }

    public void delete(Long id) {
        arquivoRepository.deleteById(id);
    }

    // --------------------------
    // Mapping helpers
    // --------------------------

    private Arquivo toEntity(ArquivoDto dto) {
        if (dto == null) {
            return null;
        }
        Arquivo entity = new Arquivo();

        // Use BeanUtils to copy standard properties where names match.
        // This helps avoid re-implementing field-by-field copying and keeps the mapping resilient to added fields.
        BeanUtils.copyProperties(dto, entity);

        // TODO: (REVIEW) Explicitly set aptos on the entity to guarantee the new field is preserved during create flows
        entity.setAptos(dto.getAptos());

        return entity;
    }

    private ArquivoDto toDto(Arquivo entity) {
        if (entity == null) {
            return null;
        }
        ArquivoDto dto = new ArquivoDto();

        // Copy common properties first
        BeanUtils.copyProperties(entity, dto);

        // TODO: (REVIEW) Explicitly set aptos on the DTO to guarantee the new field is exposed in API responses
        dto.setAptos(entity.getAptos());

        return dto;
    }
}