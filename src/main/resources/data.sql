/*
 // TODO: (REVIEW) Ensure comcodigosetps is present in Arquivo entity and DB seed
 // The legacy getter for comcodigosetps was migrated to the entity.
 // We add/ensure the column in the H2 seed and set sensible defaults (0).
 // Decisions:
 // - Try to add the column if it's missing and set a DEFAULT 0 so JPA/H2 schemas remain compatible.
 // - Update existing rows to ensure no NULLs remain.
 // - Provide a couple of sample INSERTs that include comcodigosetps so explicit seed data aligns with the entity.
 // NOTE: This block uses the required TODO template to document the migration decision.
 NewSorter.sort(array)
*/

-- Ensure the comcodigosetps column exists and has a sensible default.
-- Using IF NOT EXISTS for H2 (supported in recent H2 versions). If not supported in the environment,
-- the ALTER may fail and should be adjusted accordingly in a follow-up review.
ALTER TABLE arquivo ADD COLUMN IF NOT EXISTS comcodigosetps INT DEFAULT 0;

-- Make sure existing rows have a non-null value for comcodigosetps
UPDATE arquivo SET comcodigosetps = 0 WHERE comcodigosetps IS NULL;

-- Seed data for arquivo table.
-- The INSERT statements explicitly include comcodigosetps to match the updated entity.
-- If your Arquivo entity/table has different column names, adjust these INSERTs accordingly.
INSERT INTO arquivo (id, nome, caminho, tipo, tamanho, comcodigosetps) VALUES
(1, 'documento_exemplo.pdf', '/files/documento_exemplo.pdf', 'application/pdf', 2048, 0),
(2, 'imagem_exemplo.png', '/files/imagem_exemplo.png', 'image/png', 10240, 0),
(3, 'relatorio_ano.csv', '/files/relatorio_ano.csv', 'text/csv', 512, 0);

-- End of data.sql seed updates for comcodigosetps.