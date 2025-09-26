package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for application-level operations on Arquivo entities.
 * Focus: ensure ArquivoDto.getQuantidadeRegistro() is correctly mapped to Arquivo.setQuantidadeRegistro(...)
 * with appropriate null checks and validations.
 */
@Service
@Transactional
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    /**
     * Creates a new Arquivo from the provided DTO and persists it.
     *
     * Business rules applied:
     * - If quantidadeRegistro in DTO is null, default to 0 (assumption for new entities).
     * - quantidadeRegistro must be non-negative, otherwise an IllegalArgumentException is thrown.
     */
    public Arquivo create(ArquivoDto dto) {
        Arquivo arquivo = new Arquivo();

        // TODO: (REVIEW) Decide default for new entity when DTO.quantidadeRegistro is null.
        // We default to 0 for new Arquivo because there is no existing persisted value to preserve.
        Integer qtd = dto.getQuantidadeRegistro();
        if (qtd == null) {
            arquivo.setQuantidadeRegistro(0);
        } else {
            // TODO: (REVIEW) Validate business rule: quantidadeRegistro must be non-negative.
            if (qtd < 0) {
                throw new IllegalArgumentException("quantidadeRegistro must be non-negative");
            }
            arquivo.setQuantidadeRegistro(qtd);
        }

        // Map other fields here if necessary in the future.
        return arquivoRepository.save(arquivo);
    }

    /**
     * Updates an existing Arquivo identified by id using values from the DTO.
     *
     * Business rules applied:
     * - If quantidadeRegistro in DTO is null, preserve the existing entity's quantidadeRegistro.
     * - If provided, quantidadeRegistro must be non-negative.
     */
    public Arquivo update(Long id, ArquivoDto dto) {
        Optional<Arquivo> opt = arquivoRepository.findById(id);
        Arquivo arquivo = opt.orElseThrow(() -> new IllegalArgumentException("Arquivo not found for id: " + id));

        Integer qtd = dto.getQuantidadeRegistro();

        // TODO: (REVIEW) Decide update behavior when DTO.quantidadeRegistro is null.
        // We preserve the existing value to avoid unintentionally resetting it during partial updates.
        if (qtd != null) {
            // TODO: (REVIEW) Validate business rule: quantidadeRegistro must be non-negative.
            if (qtd < 0) {
                throw new IllegalArgumentException("quantidadeRegistro must be non-negative");
            }
            arquivo.setQuantidadeRegistro(qtd);
        }

        // Map other updatable fields from DTO to entity when needed.

        return arquivoRepository.save(arquivo);
    }

    /**
     * Retrieves an Arquivo by id.
     */
    @Transactional(readOnly = true)
    public Optional<Arquivo> findById(Long id) {
        return arquivoRepository.findById(id);
    }

    /**
     * Retrieves all Arquivo entities.
     */
    @Transactional(readOnly = true)
    public List<Arquivo> findAll() {
        return arquivoRepository.findAll();
    }

    /**
     * Deletes an Arquivo by id.
     */
    public void delete(Long id) {
        if (!arquivoRepository.existsById(id)) {
            throw new IllegalArgumentException("Arquivo not found for id: " + id);
        }
        arquivoRepository.deleteById(id);
    }
}