CREATE TABLE IF NOT EXISTS pedidos (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuarios(id),
    status VARCHAR(50),
    total NUMERIC(10,2),
    total_base NUMERIC(10,2),
    pagamento_realizado BOOLEAN,
    nome_cliente VARCHAR(255),
    data TIMESTAMP,
    observacao TEXT
); 