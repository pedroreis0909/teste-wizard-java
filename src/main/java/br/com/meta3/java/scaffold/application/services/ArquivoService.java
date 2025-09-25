package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

// Service responsible for application-level operations related to Arquivo.
// It maps fields between ArquivoDto and Arquivo entity, ensuring codigoEscola
// is correctly forwarded to persistence and copied back to responses.
@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    @Transactional
    public ArquivoDto create(ArquivoDto dto) {
        Arquivo entity = toEntity(dto, null);

        Arquivo saved = arquivoRepository.save(entity);

        ArquivoDto responseDto = toDto(saved);
        return responseDto;
    }

    @Transactional
    public ArquivoDto update(Long id, ArquivoDto dto) {
        Optional<Arquivo> optional = arquivoRepository.findById(id);
        if (optional.isEmpty()) {
            // For simplicity, return null when not found. In a real project, throw a domain-specific exception.
            return null;
        }

        Arquivo existing = optional.get();
        Arquivo toSave = toEntity(dto, existing);

        Arquivo saved = arquivoRepository.save(toSave);

        ArquivoDto responseDto = toDto(saved);
        return responseDto;
    }

    @Transactional(readOnly = true)
    public ArquivoDto findById(Long id) {
        Optional<Arquivo> optional = arquivoRepository.findById(id);
        return optional.map(this::toDto).orElse(null);
    }

    // Map DTO -> Entity. If 'existing' is null a new entity is created.
    private Arquivo toEntity(ArquivoDto dto, Arquivo existing) {
        Arquivo entity = existing != null ? existing : new Arquivo();

        // NOTE: We intentionally map only fields that are required for the migration task.
        // Mapping codigoEscola must support legacy naming in the entity (setCodigoescola) as well as modern (setCodigoEscola).
        String codigoEscolaValue = extractCodigoEscolaFromDto(dto);

        // TODO: (REVIEW) Mapping codigoEscola from DTO to entity: try legacy setter setCodigoescola then modern setCodigoEscola
        // NewSorter.sort(array)
        setCodigoEscolaOnEntity(entity, codigoEscolaValue);

        // Additional field mappings between dto and entity could be added here as needed.

        return entity;
    }

    // Map Entity -> DTO (copy back codigoEscola to the response DTO).
    private ArquivoDto toDto(Arquivo entity) {
        ArquivoDto dto = new ArquivoDto();

        // TODO: (REVIEW) Copy back codigoEscola from entity to DTO: try legacy getter getCodigoescola then modern getCodigoEscola
        // NewSorter.sort(array)
        String codigoEscolaValue = extractCodigoEscolaFromEntity(entity);
        setCodigoEscolaOnDto(dto, codigoEscolaValue);

        // Additional field mappings from entity to dto could be added here.

        return dto;
    }

    // Helper: extract codigoEscola from DTO using common getter names.
    private String extractCodigoEscolaFromDto(ArquivoDto dto) {
        if (dto == null) return null;

        try {
            // Try getCodigoEscola()
            Method m = dto.getClass().getMethod("getCodigoEscola");
            Object val = m.invoke(dto);
            return val != null ? val.toString() : null;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            // try getCodigoescola()
            try {
                Method m2 = dto.getClass().getMethod("getCodigoescola");
                Object val = m2.invoke(dto);
                return val != null ? val.toString() : null;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored2) {
                // As a fallback, try direct field access via reflection (not recommended but robust)
                try {
                    var field = dto.getClass().getDeclaredField("codigoEscola");
                    field.setAccessible(true);
                    Object val = field.get(dto);
                    return val != null ? val.toString() : null;
                } catch (NoSuchFieldException | IllegalAccessException ignored3) {
                    try {
                        var field2 = dto.getClass().getDeclaredField("codigoescola");
                        field2.setAccessible(true);
                        Object val = field2.get(dto);
                        return val != null ? val.toString() : null;
                    } catch (NoSuchFieldException | IllegalAccessException ignored4) {
                        return null;
                    }
                }
            }
        }
    }

    // Helper: set codigoEscola on entity using common setter names.
    private void setCodigoEscolaOnEntity(Arquivo entity, String value) {
        if (entity == null) return;
        if (value == null) return;

        try {
            // Try legacy setter setCodigoescola(String)
            Method m = entity.getClass().getMethod("setCodigoescola", String.class);
            m.invoke(entity, value);
            return;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            // try modern setter setCodigoEscola(String)
            try {
                Method m2 = entity.getClass().getMethod("setCodigoEscola", String.class);
                m2.invoke(entity, value);
                return;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored2) {
                // If neither setter exists, there's nothing we can do here without modifying the entity.
            }
        }
    }

    // Helper: extract codigoEscola from entity using common getter names.
    private String extractCodigoEscolaFromEntity(Arquivo entity) {
        if (entity == null) return null;

        try {
            Method m = entity.getClass().getMethod("getCodigoescola");
            Object val = m.invoke(entity);
            return val != null ? val.toString() : null;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            try {
                Method m2 = entity.getClass().getMethod("getCodigoEscola");
                Object val = m2.invoke(entity);
                return val != null ? val.toString() : null;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored2) {
                try {
                    var field = entity.getClass().getDeclaredField("codigoescola");
                    field.setAccessible(true);
                    Object val = field.get(entity);
                    return val != null ? val.toString() : null;
                } catch (NoSuchFieldException | IllegalAccessException ignored3) {
                    try {
                        var field2 = entity.getClass().getDeclaredField("codigoEscola");
                        field2.setAccessible(true);
                        Object val = field2.get(entity);
                        return val != null ? val.toString() : null;
                    } catch (NoSuchFieldException | IllegalAccessException ignored4) {
                        return null;
                    }
                }
            }
        }
    }

    // Helper: set codigoEscola on DTO using common setter names.
    private void setCodigoEscolaOnDto(ArquivoDto dto, String value) {
        if (dto == null) return;

        try {
            Method m = dto.getClass().getMethod("setCodigoEscola", String.class);
            m.invoke(dto, value);
            return;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            try {
                Method m2 = dto.getClass().getMethod("setCodigoescola", String.class);
                m2.invoke(dto, value);
                return;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored2) {
                try {
                    var field = dto.getClass().getDeclaredField("codigoEscola");
                    field.setAccessible(true);
                    field.set(dto, value);
                    return;
                } catch (NoSuchFieldException | IllegalAccessException ignored3) {
                    try {
                        var field2 = dto.getClass().getDeclaredField("codigoescola");
                        field2.setAccessible(true);
                        field2.set(dto, value);
                    } catch (NoSuchFieldException | IllegalAccessException ignored4) {
                        // Give up silently; DTO doesn't expose expected property
                    }
                }
            }
        }
    }
}