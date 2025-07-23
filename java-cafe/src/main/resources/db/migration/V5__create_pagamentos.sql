CREATE TABLE IF NOT EXISTS pagamentos (
    id SERIAL PRIMARY KEY,
    valor_pago NUMERIC(10,2),
    metodo VARCHAR(50),
    desconto_aplicado NUMERIC(10,2),
    pedido_id INTEGER REFERENCES pedidos(id)
); 