CREATE TABLE IF NOT EXISTS ingredientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco_adicional NUMERIC(10,2) DEFAULT 0,
    ativo BOOLEAN DEFAULT true
);

INSERT INTO ingredientes (nome, preco_adicional, ativo) VALUES
    ('Leite de Aveia', 2.00, true),
    ('Canela', 1.00, true),
    ('Sem Açúcar', 0.00, true),
    ('Pitada de Hortelã', 1.50, true),
    ('Gelo Extra', 0.50, true),
    ('Limão', 1.00, true),
    ('Mel', 1.50, true),
    ('Chantilly', 2.00, true),
    ('Chocolate', 2.00, true),
    ('Açúcar Mascavo', 0.50, true); 