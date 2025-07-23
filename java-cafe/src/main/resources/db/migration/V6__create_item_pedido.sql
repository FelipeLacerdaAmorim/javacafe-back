CREATE TABLE IF NOT EXISTS item_pedido (
    id SERIAL PRIMARY KEY,
    quantidade INTEGER,
    preco_unitario NUMERIC(10,2),
    subtotal NUMERIC(10,2),
    produto_id INTEGER REFERENCES produtos(id),
    pedido_id INTEGER REFERENCES pedidos(id)
); 