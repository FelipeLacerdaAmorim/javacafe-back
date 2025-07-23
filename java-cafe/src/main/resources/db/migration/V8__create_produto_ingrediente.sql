CREATE TABLE IF NOT EXISTS produto_ingrediente (
    produto_id INTEGER REFERENCES produtos(id),
    ingrediente_id INTEGER REFERENCES ingredientes(id),
    PRIMARY KEY (produto_id, ingrediente_id)
); 