CREATE TABLE IF NOT EXISTS item_pedido_ingredientes (
    item_pedido_id INTEGER REFERENCES item_pedido(id),
    ingrediente_id INTEGER REFERENCES ingredientes(id),
    PRIMARY KEY (item_pedido_id, ingrediente_id)
); 