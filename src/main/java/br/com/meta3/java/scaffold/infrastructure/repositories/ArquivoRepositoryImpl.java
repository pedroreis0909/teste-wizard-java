package br.com.meta3.java.scaffold.infrastructure.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;

/**
 * Adapter implementation that delegates domain persistence operations to the
 * Spring Data repository (SpringDataArquivoRepository). This preserves the
 * domain-level ArquivoRepository abstraction so application services remain
 * decoupled from Spring Data specifics.
 */
@Repository
public class ArquivoRepositoryImpl implements ArquivoRepository {

    private final SpringDataArquivoRepository springDataArquivoRepository;

    public ArquivoRepositoryImpl(SpringDataArquivoRepository springDataArquivoRepository) {
        this.springDataArquivoRepository = springDataArquivoRepository;
    }

    @Override
    @Transactional
    public Arquivo save(Arquivo arquivo) {
        return springDataArquivoRepository.save(arquivo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Arquivo> findById(Long id) {
        return springDataArquivoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Arquivo> findAll() {
        return springDataArquivoRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        springDataArquivoRepository.deleteById(id);
    }

    // Non-overridden convenience method delegating to Spring Data.
    // This method is intentionally exposed to the infrastructure layer and can be
    // used by other infra components if needed; it is not required by the domain interface.
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return springDataArquivoRepository.existsById(id);
    }

    // TODO: (REVIEW) Adapting domain ArquivoRepository to delegate to SpringDataArquivoRepository
    // We keep this implementation intentionally thin: each domain operation maps directly to a
    // Spring Data call. This preserves the domain abstraction and makes future changes to the
    // persistence technology (or unit testing with mocks) straightforward.
}