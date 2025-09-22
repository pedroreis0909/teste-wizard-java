package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Application service that exposes CRUD operations for Arquivo entities.
 * It performs validation of incoming DTOs and maps between DTOs and entities.
 */
@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;
    private final Validator validator;

    @Autowired
    public ArquivoService(ArquivoRepository arquivoRepository, Validator validator) {
        this.arquivoRepository = arquivoRepository;
        this.validator = validator;
    }

    // TODO: (REVIEW) Using BeanUtils.copyProperties to map between DTO and entity for brevity
    // BeanUtils requires matching property names on source/target. This avoids manual field-by-field mapping
    // while keeping the service simple. If DTO/entity shapes diverge, consider replacing with MapStruct or manual mapping.
    @Transactional
    public ArquivoDto create(ArquivoDto arquivoDto) {
        validateDto(arquivoDto);

        Arquivo entity = new Arquivo();
        BeanUtils.copyProperties(arquivoDto, entity);

        // TODO: (REVIEW) Assuming ArquivoRepository exposes a save method consistent with Spring Data conventions.
        Arquivo saved = arquivoRepository.save(entity);

        ArquivoDto result = new ArquivoDto();
        BeanUtils.copyProperties(saved, result);
        return result;
    }

    // TODO: (REVIEW) We validate provided id and rely on repository.findById to return Optional.
    // If not found, we throw EntityNotFoundException which leads to a 500 by default; controllers can translate it to 404.
    @Transactional(readOnly = true)
    public ArquivoDto findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Arquivo> optional = arquivoRepository.findById(id);
        Arquivo entity = optional.orElseThrow(() -> new EntityNotFoundException("Arquivo not found with id: " + id));

        ArquivoDto dto = new ArquivoDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ArquivoDto> listAll() {
        // TODO: (REVIEW) Assuming repository.findAll() returns a List<Arquivo>.
        // We map each entity to DTO using BeanUtils for simplicity.
        List<Arquivo> entities = arquivoRepository.findAll();
        return entities.stream().map(entity -> {
            ArquivoDto dto = new ArquivoDto();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    // TODO: (REVIEW) delete operation first checks existence to provide a clearer exception if missing.
    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Arquivo> optional = arquivoRepository.findById(id);
        Arquivo entity = optional.orElseThrow(() -> new EntityNotFoundException("Arquivo not found with id: " + id));

        // TODO: (REVIEW) Assuming repository.delete(...) exists. If only deleteById is available,
        // the implementation can be adjusted to call arquivoRepository.deleteById(id).
        arquivoRepository.delete(entity);
    }

    // TODO: (REVIEW) Validate DTO using Jakarta Validator to honor annotations on the DTO class.
    // This avoids manual null checks here and centralizes validation rules in the DTO.
    private void validateDto(ArquivoDto arquivoDto) {
        if (arquivoDto == null) {
            throw new IllegalArgumentException("arquivoDto must not be null");
        }
        Set<ConstraintViolation<ArquivoDto>> violations = validator.validate(arquivoDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}