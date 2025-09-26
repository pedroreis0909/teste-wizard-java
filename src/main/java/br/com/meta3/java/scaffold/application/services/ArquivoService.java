package br.com.meta3.java.scaffold.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;

@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    @Transactional
    public ArquivoDto create(ArquivoDto dto) {
        // Map DTO -> Entity
        Arquivo entity = new Arquivo();

        // TODO: (REVIEW) Legacy code provided a setter for 'anovigencia'; preserve exact value mapping here
        // Copying the legacy field from DTO to Entity so the persistence layer receives the value.
        entity.setAnovigencia(dto.getAnovigencia());

        Arquivo saved = arquivoRepository.save(entity);

        // Map Entity -> DTO for response
        return mapToDto(saved);
    }

    @Transactional
    public ArquivoDto update(Long id, ArquivoDto dto) {
        Arquivo entity = arquivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo not found with id: " + id));

        // TODO: (REVIEW) Ensure update preserves legacy 'anovigencia' field; copy value from incoming DTO
        // This mirrors the legacy setter behavior in update flows.
        entity.setAnovigencia(dto.getAnovigencia());

        Arquivo saved = arquivoRepository.save(entity);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public ArquivoDto findById(Long id) {
        Arquivo entity = arquivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo not found with id: " + id));
        return mapToDto(entity);
    }

    @Transactional(readOnly = true)
    public List<ArquivoDto> findAll() {
        return arquivoRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ArquivoDto mapToDto(Arquivo entity) {
        ArquivoDto dto = new ArquivoDto();

        // TODO: (REVIEW) Mapping legacy field 'anovigencia' from Entity to DTO to preserve read flows
        // Ensure the DTO receives the persisted legacy value.
        dto.setAnovigencia(entity.getAnovigencia());

        return dto;
    }

    // The explicit to-entity mapper is kept minimal to avoid assumptions about other fields.
    // TODO: (REVIEW) If additional fields need mapping, extend this method while preserving 'anovigencia' handling.
    private Arquivo mapToEntity(ArquivoDto dto) {
        Arquivo entity = new Arquivo();
        // TODO: (REVIEW) Preserve legacy setter usage for 'anovigencia' in create/update flows.
        entity.setAnovigencia(dto.getAnovigencia());
        return entity;
    }
}