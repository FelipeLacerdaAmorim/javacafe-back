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

### Pré-requisitos

- Docker instalado e funcionando na sua máquina
- Docker Compose (versão compatível)

---

### Passos para rodar a aplicação

1. Clone o repositório:

```bash
git clone https://github.com/FelipeLacerdaAmorim/javacafe-back.git
cd javacafe-back
```

2. Configure o arquivo de variavel de ambiente .env com seu usuario e senha do banco:
```env
POSTGRES_DB_USER=<usuario>
POSTGRES_DB_PASSWORD=<senha>
```

3. Gerar build do projeto:
```bash
mvn clean package install
```

4. Executar compose:
```bash
## No diretório do docker-compose.yml
docker-compose up -d
```