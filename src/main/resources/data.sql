// TODO: (REVIEW) Using CREATE TABLE IF NOT EXISTS to ensure Arquivo exists without overwriting existing schema
CREATE TABLE IF NOT EXISTS Arquivo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    aptos INT DEFAULT 0
);

// TODO: (REVIEW) Using ALTER TABLE ADD COLUMN IF NOT EXISTS for H2 to add 'aptos' safely when table already exists
ALTER TABLE Arquivo ADD COLUMN IF NOT EXISTS aptos INT DEFAULT 0;

// TODO: (REVIEW) Initialize existing rows where 'aptos' is NULL to a safe default (0)
UPDATE Arquivo SET aptos = 0 WHERE aptos IS NULL;