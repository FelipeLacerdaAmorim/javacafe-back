CREATE TABLE IF NOT EXISTS produtos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    preco NUMERIC(10,2) NOT NULL,
    tipo VARCHAR(50),
    categoria VARCHAR(50),
    imagem_url VARCHAR(255),
    ativo BOOLEAN DEFAULT true
);

INSERT INTO produtos (nome, descricao, preco, tipo, categoria, imagem_url, ativo) VALUES
    ('Café Expresso', 'Café expresso forte e encorpado', 5.00, 'BEBIDA', 'Cafés', 'https://cdn.sistemawbuy.com.br/arquivos/f101ef5b9be464a6c8854310ae5f5327/produtos/65bb164199b0d/ea6b9e38be0122aedce63daa0891062e-65bb1642a0121.jpg', true),
    ('Café Latte', 'Café com leite cremoso', 7.50, 'BEBIDA', 'Cafés', 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/Caffe_Latte_at_Pulse_Cafe.jpg/1200px-Caffe_Latte_at_Pulse_Cafe.jpg', true),
    ('Chá Verde', 'Chá verde natural e refrescante', 4.00, 'BEBIDA', 'Chás', 'https://jpimg.com.br/uploads/2024/07/7-beneficios-do-cha-verde-para-a-saude.jpg', true),
    ('Chá Preto', 'Chá preto tradicional', 3.50, 'BEBIDA', 'Chás', 'https://replantea.eu/cdn/shop/articles/Tipos_de_te_negro_1080x.jpg?v=1633106545', true),
    ('Suco de Laranja', 'Suco natural de laranja', 6.00, 'BEBIDA', 'Sucos', 'https://www.citrosuco.com.br/wp-content/uploads/2022/02/THUMB-05.png', true),
    ('Suco de Abacaxi', 'Suco natural de abacaxi', 6.50, 'BEBIDA', 'Sucos', 'https://www.oitedi.com.br/_next/image?url=https%3A%2F%2Ftedi-production.s3.amazonaws.com%2Fcooking_recipes%2Ffood_description%2F92736157eed977ee0a59cd2f240e3c946d2e8cc6.png&w=1080&q=70', true),
    ('Brownie de Chocolate', 'Brownie artesanal com pedaços de chocolate', 8.00, 'SOBREMESA', 'Doces', 'https://bakeandcakegourmet.com.br/uploads/site/receitas/brownie-a61d7xl1.jpg', true),
    ('Torta de Limão', 'Torta de limão com merengue', 9.50, 'SOBREMESA', 'Tortas', 'https://anamariabrogui.com.br/assets/uploads/receitas/fotos/usuario-3165-b1eacedcc7ed6e308193504b1cd0fe74.jpg', true),
    ('Cheesecake de Frutas Vermelhas', 'Cheesecake com calda de frutas vermelhas', 10.00, 'SOBREMESA', 'Tortas', 'https://anamariabrogui.com.br/assets/uploads/receitas/fotos/usuario-2203-e114cfac7e3ff236ac42543f014f542b.jpeg', true),
    ('Pão de Queijo', 'Porção com 3 unidades de pão de queijo artesanal', 4.00, 'ACOMPANHAMENTO', 'Salgados', 'https://cdn.oceanserver.com.br/lojas/eat/uploads_produto/pao-de-queijojpeg-62011aa6d5fb8.jpeg', true),
    ('Biscoito Artesanal', 'Biscoitos doces crocantes com canela', 3.50, 'ACOMPANHAMENTO', 'Doces', 'https://www.sabornamesa.com.br/media/k2/items/cache/6783792c7419427295335dc3ce02b000_XL.jpg', true),
    ('Mini Sanduíche', 'Mini sanduíche natural de frango', 5.50, 'ACOMPANHAMENTO', 'Salgados', 'https://i.pinimg.com/736x/aa/7a/e0/aa7ae08d5f64cd5462d9f4c6f1070eac.jpg', true); 