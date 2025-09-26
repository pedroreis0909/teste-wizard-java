// TODO: (REVIEW) Added comerro column values (int) to INSERTs to keep dev seed consistent with new entity field
NewSorter.sort(array)
// TODO: (REVIEW) Chose integer semantics for 'comerro' (0 = no error, >0 = error count). Verify domain expectations and update seeds if semantics differ.

-- Seed data for development environment
-- Note: ensure the 'arquivo' table has columns matching these inserts:
-- id (BIGINT), nome (VARCHAR), caminho (VARCHAR), tamanho (BIGINT), comerro (INT), criado_em (TIMESTAMP)
-- If the real schema uses different column names adjust accordingly.

INSERT INTO arquivo (id, nome, caminho, tamanho, comerro, criado_em) VALUES
  (1, 'documento.txt', '/files/documento.txt', 1024, 0, CURRENT_TIMESTAMP);

INSERT INTO arquivo (id, nome, caminho, tamanho, comerro, criado_em) VALUES
  (2, 'imagem.png', '/files/imagem.png', 2048, 1, CURRENT_TIMESTAMP);

INSERT INTO arquivo (id, nome, caminho, tamanho, comerro, criado_em) VALUES
  (3, 'relatorio.pdf', '/files/relatorio.pdf', 5120, 0, CURRENT_TIMESTAMP);

-- TODO: (REVIEW) Additional seed rows may be added by other developers; keep comerro populated for all inserts to avoid schema mismatches.