# API de Pessoas

Aplicação REST Spring Boot com persistência de pessoas em PostgreSQL.

## Organização do código

A aplicação segue uma organização por camadas sob `com.example.people`. A classe principal `PeopleApplication` fica no pacote-raiz, permitindo que o Spring descubra automaticamente os componentes e entidades:

- `controller`: endpoints REST de pessoas.
- `dto`: objetos de entrada e saída da API.
- `entity`: entidade JPA `Pessoa` e entidade `Endereco`.
- `repository`: acesso aos dados com Spring Data JPA.
- `service`: regras de negócio e operações de pessoas.

Os testes ficam em `src/test/java`, espelhando o pacote-raiz da aplicação.

## Iniciar o PostgreSQL

Na pasta do projeto, configure uma senha local para o PostgreSQL e inicie o banco:

```bash
export DATABASE_PASSWORD='escolha-uma-senha-local'
docker compose up -d
```

O banco fica disponível em `localhost:5434`, no database `pessoas_db`, com o usuário `pessoas`. A aplicação e o Docker Compose usam `DATABASE_PASSWORD`; configure essa variável no ambiente antes de iniciar os serviços. Não compartilhe nem versione a senha.

## Executar a aplicação

Requer Java 17 ou superior e Maven.

```bash
mvn spring-boot:run
```

## API de pessoas

Cadastre uma pessoa com nome e e-mail:

```bash
curl -i -X POST http://localhost:8080/api/pessoas \
  -H "Content-Type: application/json" \
  -d '{"nome":"Ana Silva","email":"ana@example.com"}'
```

O cadastro também aceita o CPF e uma lista opcional de endereços. Cada endereço contém descrição e CEP:

```bash
curl -i -X POST http://localhost:8080/api/pessoas \
  -H "Content-Type: application/json" \
  -d '{"nome":"Ana Silva","email":"ana@example.com","cpf":"12345678901","enderecos":[{"endereco":"Rua das Flores, 10","cep":"01001000"},{"endereco":"Av. Brasil, 20","cep":"01001000"}]}'
```

A resposta tem status `201 Created` e inclui o ID gerado. Consulte todas as pessoas:

```bash
curl http://localhost:8080/api/pessoas
```

Consulte uma pessoa pelo ID retornado no cadastro:

```bash
curl http://localhost:8080/api/pessoas/1
```

Busque pessoas pelo e-mail (a busca é exata e pode retornar mais de um resultado):

```bash
curl --get --data-urlencode "email=ana@example.com" http://localhost:8080/api/pessoas
```

Busque pessoas pelo CEP:

```bash
curl --get --data-urlencode "cep=01001000" http://localhost:8080/api/pessoas
```

O cadastro exige nome não vazio e e-mail válido. CPF e a lista de endereços são opcionais; cada endereço informado exige descrição e CEP. Uma pessoa pode ter vários endereços. Um ID inexistente retorna `404 Not Found`; buscas sem correspondências retornam uma lista vazia.

## Testes

```bash
mvn test
```

Os testes usam um banco H2 em memória; não precisam iniciar o PostgreSQL.
