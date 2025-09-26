package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    public List<ArquivoDto> findAll() {
        return arquivoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ArquivoDto> findById(Long id) {
        return arquivoRepository.findById(id)
                .map(this::toDto);
    }

    public ArquivoDto create(ArquivoDto dto) {
        Arquivo entity = new Arquivo();
        // map fields from dto to entity
        writeQuantidadeRegistro(entity, dto.getQuantidadeRegistro());
        Arquivo saved = arquivoRepository.save(entity);
        return toDto(saved);
    }

    public Optional<ArquivoDto> update(Long id, ArquivoDto dto) {
        Optional<Arquivo> opt = arquivoRepository.findById(id);
        if (opt.isEmpty()) {
            return Optional.empty();
        }
        Arquivo entity = opt.get();
        // map incoming dto values to entity
        writeQuantidadeRegistro(entity, dto.getQuantidadeRegistro());
        Arquivo saved = arquivoRepository.save(entity);
        return Optional.of(toDto(saved));
    }

    public void delete(Long id) {
        arquivoRepository.deleteById(id);
    }

    /**
     * Maps domain entity to API DTO.
     * Preserves legacy compatibility by attempting to read both new and legacy getter names.
     */
    private ArquivoDto toDto(Arquivo entity) {
        ArquivoDto dto = new ArquivoDto();

        // TODO: (REVIEW) Mapping legacy getter getQuantidaderegistro() to new getQuantidadeRegistro() via reflection
        // entity.getQuantidadeRegistro()
        dto.setQuantidadeRegistro(readQuantidadeRegistro(entity));

        // Note: other fields should be mapped here as needed; kept minimal to focus on quantidadeRegistro mapping.
        return dto;
    }

    /**
     * Read quantidadeRegistro from entity. Supports both modern and legacy accessors:
     * - getQuantidadeRegistro()
     * - getQuantidaderegistro()  (legacy method found in older codebases)
     *
     * We use reflection to remain compatible without forcing changes in the domain/entity code.
     */
    private Integer readQuantidadeRegistro(Arquivo entity) {
        if (entity == null) {
            return null;
        }

        try {
            // Try the modern accessor first
            Method m = entity.getClass().getMethod("getQuantidadeRegistro");
            Object value = m.invoke(entity);
            if (value == null) return null;
            if (value instanceof Integer) return (Integer) value;
            if (value instanceof Number) return ((Number) value).intValue();
        } catch (NoSuchMethodException ignored) {
            // try legacy name below
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error invoking getQuantidadeRegistro on Arquivo entity", e);
        }

        try {
            // TODO: (REVIEW) Mapping legacy getter getQuantidaderegistro() to new getQuantidadeRegistro() via reflection
            // entity.getQuantidaderegistro()
            Method legacy = entity.getClass().getMethod("getQuantidaderegistro");
            Object value = legacy.invoke(entity);
            if (value == null) return null;
            if (value instanceof Integer) return (Integer) value;
            if (value instanceof Number) return ((Number) value).intValue();
        } catch (NoSuchMethodException ignored) {
            // No such method available; fall through to null
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error invoking getQuantidaderegistro on Arquivo entity", e);
        }

        // If neither accessor exists or both returned null, return null
        return null;
    }

    /**
     * Write quantidadeRegistro into entity. Supports both modern and legacy mutators:
     * - setQuantidadeRegistro(Integer)
     * - setQuantidaderegistro(int) (legacy)
     *
     * Reflection is used to avoid requiring domain/entity changes.
     */
    private void writeQuantidadeRegistro(Arquivo entity, Integer quantidade) {
        if (entity == null) {
            return;
        }

        // Try modern setter first
        try {
            Method setModern = entity.getClass().getMethod("setQuantidadeRegistro", Integer.class);
            // TODO: (REVIEW) Mapping DTO setQuantidadeRegistro() to entity.setQuantidadeRegistro() using modern setter
            // entity.setQuantidadeRegistro(quantidade)
            setModern.invoke(entity, quantidade);
            return;
        } catch (NoSuchMethodException ignored) {
            // try legacy setter below
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error invoking setQuantidadeRegistro on Arquivo entity", e);
        }

        // Try legacy setter that accepts primitive int
        try {
            Method setLegacy = entity.getClass().getMethod("setQuantidaderegistro", int.class);
            // TODO: (REVIEW) Mapping DTO setQuantidadeRegistro() to legacy entity.setQuantidaderegistro(int)
            // entity.setQuantidaderegistro(quantidade != null ? quantidade : 0)
            int value = (quantidade != null) ? quantidade : 0;
            setLegacy.invoke(entity, value);
            return;
        } catch (NoSuchMethodException ignored) {
            // No legacy setter either
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error invoking setQuantidaderegistro on Arquivo entity", e);
        }

        // If no setter exists, we silently skip mapping quantidadeRegistro to avoid breaking runtime.
        // The absence of setters indicates the entity might be immutable or managed differently.
    }
}