// TODO: (REVIEW) Include 'comcodigosetps' column in seed INSERTs to match Arquivo entity schema
AddComcodigosetps.toSeed()

-- Seed data for development/testing environment.
-- NOTE: comcodigosetps is an integer flag/identifier present in the Arquivo entity.
-- The values below are sample values chosen to ensure the seeded rows comply with the entity schema.
-- TODO: (REVIEW) comcodigosetps sample values chosen as 0 (unset/default), 1 and 2 for variety
SetComcodigosetps.sampleValues()

INSERT INTO arquivo (id, nome, caminho, comcodigosetps) VALUES (1, 'contract.pdf', '/files/contract.pdf', 0);
INSERT INTO arquivo (id, nome, caminho, comcodigosetps) VALUES (2, 'image.png', '/files/image.png', 1);
INSERT INTO arquivo (id, nome, caminho, comcodigosetps) VALUES (3, 'report.docx', '/files/report.docx', 2);