API REST desenvolvida com Spring Boot para gerenciamento de hóspedes e reservas.

## Tecnologias que utilizei

- Java 17
- Spring Boot
- PostgreSQL
- Docker
- Maven

## Requisitos para rodar

- Docker
- Java 17
- Maven

---

## Subir banco de dados

Executar:

```bash
docker compose up -d
```

Banco PostgreSQL:

| Campo    | Valor     |
|----------|-----------|
| Host     | localhost |
| Porta    | 5432      |
| Database | hotel     |
| Usuário  | postgres  |
| Senha    | postgres  |

---

## Rodar aplicação

- Clonar esse projeto em alguma pasta local.
- Abrir alguma IDE do Java (utilizei IntelliJ IDEA 2025.3.3).
- Abrir a pasta clonada do git dentro da IDE.

Instalar dependências:

```bash
mvn clean install ou mvnw.cmd clean install
```

Subir aplicação localmente:

```bash
mvn spring-boot:run ou mvnw.cmd spring-boot:run
```

Aplicação disponível em:

```text
http://localhost:8080
```

---

## Collection Postman

Importe no postman a collection para realizar as requisições:
https://we.tl/t-TFRWVNPAmWBTy6BO

![img_1.png](img_1.png)
