package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@Validated
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    // TODO: (REVIEW) Ensure service-level validation is applied for incoming DTOs.
    // Using @Validated on the class and @Valid on method parameters allows Spring to
    // propagate validation errors to the controller/advice layer.
    // @Valid is applied on create/update methods below.
    // Arquivo entity = new Arquivo();

    @Transactional
    public ArquivoDto createArquivo(@Valid ArquivoDto arquivoDto) {
        Arquivo entity = toEntity(arquivoDto);
        Arquivo saved = arquivoRepository.save(entity);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<ArquivoDto> listAll() {
        // TODO: (REVIEW) Decided to map repository results to DTOs here to keep controller thin.
        // Mapping done via toDto to avoid exposing entity to controllers.
        // Arquivo entity = new Arquivo();
        List<Arquivo> all = arquivoRepository.findAll();
        return all.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArquivoDto findById(Long id) {
        Optional<Arquivo> opt = arquivoRepository.findById(id);
        Arquivo entity = opt.orElseThrow(() -> new IllegalArgumentException("Arquivo not found with id: " + id));
        return toDto(entity);
    }

    @Transactional
    public ArquivoDto updateArquivo(Long id, @Valid ArquivoDto arquivoDto) {
        Arquivo existing = arquivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Arquivo not found with id: " + id));

        // TODO: (REVIEW) Replaced legacy setNomearquivo/getNomearquivo usage with camelCase setNomeArquivo/getNomeArquivo
        // This preserves original functionality while matching Java naming conventions used in the new entity.
        // Arquivo entity = new Arquivo();
        existing.setNomeArquivo(arquivoDto.getNomeArquivo());

        // If there are other fields on the DTO that need updating, set them here as well.
        Arquivo saved = arquivoRepository.save(existing);
        return toDto(saved);
    }

    @Transactional
    public void deleteArquivo(Long id) {
        if (!arquivoRepository.existsById(id)) {
            throw new IllegalArgumentException("Arquivo not found with id: " + id);
        }
        arquivoRepository.deleteById(id);
    }

    // TODO: (REVIEW) Manual mapping between DTO and Entity chosen to avoid introducing additional mapper dependencies.
    // This keeps the migration simple and explicit.
    // Arquivo entity = new Arquivo();
    private Arquivo toEntity(ArquivoDto dto) {
        if (dto == null) {
            return null;
        }
        Arquivo entity = new Arquivo();

        // Preserve id if provided (useful for upserts or tests)
        try {
            // Some DTOs may not have id; guard with reflection not necessary here as we assume typical DTO fields.
            entity.setId(dto.getId());
        } catch (Exception ex) {
            // ignore missing id setter/getter in DTO - defensive approach
        }

        // IMPORTANT: Use setNomeArquivo (camelCase) as per new entity API.
        entity.setNomeArquivo(dto.getNomeArquivo());

        // TODO: (REVIEW) If the DTO contains more fields, map them here explicitly.
        // Arquivo entity2 = new Arquivo();

        return entity;
    }

    private ArquivoDto toDto(Arquivo entity) {
        if (entity == null) {
            return null;
        }
        ArquivoDto dto = new ArquivoDto();
        try {
            dto.setId(entity.getId());
        } catch (Exception ex) {
            // ignore if DTO doesn't expose id
        }
        dto.setNomeArquivo(entity.getNomeArquivo());
        // TODO: (REVIEW) Map additional entity fields to DTO here as needed.
        // Arquivo entity3 = new Arquivo();
        return dto;
    }
}