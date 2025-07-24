# javacafe-back

## Descrição

Backend para o sistema de pedidos personalizados para cafeteria, implementado em Spring Boot com Java 24.

---

## Tecnologias Utilizadas

- Java 24
- Spring Boot
- Docker & Docker Compose
- Lombok
- JPA / Hibernate
- PostgreSQL (container Docker)

---

## Como Executar

### Opção 1: Rodando TUDO via Docker (recomendado para quem NÃO tem Java instalado)

> **Você só precisa do Docker instalado!**

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/FelipeLacerdaAmorim/javacafe-back.git
   cd javacafe-back
   ```

2. **Configure o arquivo `.env`** (opcional, já vem com padrão):
   ```env
   POSTGRES_DB_USER=postgres
   POSTGRES_DB_PASSWORD=postgres
   ```

3. **Suba tudo com Docker Compose:**
   ```bash
   docker-compose up --build -d
   ```
   Isso irá:
   - Subir o banco PostgreSQL
   - Fazer build e rodar a aplicação Java automaticamente

4. **Acesse a API:**
   - Por padrão, estará em: [http://localhost:8080](http://localhost:8080)

5. **Parar tudo:**
   ```bash
   docker-compose down
   ```

---

### Opção 2: Rodando o banco via Docker e a aplicação localmente (console)

> **Você precisa do Docker para o banco e do Java 17+ e Maven instalados para rodar a aplicação localmente.**

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/FelipeLacerdaAmorim/javacafe-back.git
   cd javacafe-back
   ```

2. **Configure o arquivo `.env`** (opcional, já vem com padrão):
   ```env
   POSTGRES_DB_USER=postgres
   POSTGRES_DB_PASSWORD=postgres
   ```

3. **Suba apenas o banco de dados:**
   ```bash
   docker-compose up -d db
   ```
   Isso irá subir apenas o container do PostgreSQL.

4. **Build e rode a aplicação Java localmente:**
   ```bash
   cd java-cafe
   mvn clean package
   java -jar target/*.jar
   ```
   Ou rode direto pelo Maven:
   ```bash
   mvn spring-boot:run
   ```

5. **Acesse a API:**
   - Por padrão, estará em: [http://localhost:8080](http://localhost:8080)

6. **Parar o banco:**
   ```bash
   docker-compose down
   ```

---

## Observações
- O projeto já cria as tabelas e dados iniciais automaticamente.
- Se precisar alterar portas ou configs, edite o `docker-compose.yml` e/ou `application.yml`.
- Para dúvidas, consulte a documentação dos endpoints ou abra uma issue.
