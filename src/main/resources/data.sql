// TODO: (REVIEW) Defaulting semdocumento to 0 for seeded Arquivo records to match new entity setter setSemdocumento(int)
// Reason: Legacy code exposes setSemdocumento(int). Treat semdocumento as an integer flag (0 = no document, 1 = has document).
-- Updated seed data to include semdocumento column so initial DB state matches the updated entity shape.

/* Idempotent seed: remove existing rows in arquivo (if any) then insert seed rows with semdocumento values.
   NOTE: Ensure the Arquivo entity/table contains the semdocumento column (int). If using JPA auto-ddl, the column
   should be created from the entity. If not present, add migration to create the column before this seed runs.
*/

// Delete existing data to make the seed idempotent
DELETE FROM arquivo;

-- Seed records: semdocumento set to 0 by default for typical files, 1 where applicable.
INSERT INTO arquivo (id, nome, tipo, tamanho, semdocumento) VALUES (1, 'documento1.pdf', 'application/pdf', 1024, 0);
INSERT INTO arquivo (id, nome, tipo, tamanho, semdocumento) VALUES (2, 'imagem1.png', 'image/png', 2048, 0);
INSERT INTO arquivo (id, nome, tipo, tamanho, semdocumento) VALUES (3, 'relatorio.docx', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 4096, 1);
INSERT INTO arquivo (id, nome, tipo, tamanho, semdocumento) VALUES (4, 'planilha.xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 5120, 0);
INSERT INTO arquivo (id, nome, tipo, tamanho, semdocumento) VALUES (5, 'apresentacao.pptx', 'application/vnd.openxmlformats-officedocument.presentationml.presentation', 8192, 0);