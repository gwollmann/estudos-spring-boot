# Diretrizes do projeto

## Stack e organização

- Mantenha a compatibilidade com Java 17, Spring Boot 3.5.x e Maven, conforme `pom.xml`.
- Preserve a organização por camadas em `com.example.people`: controllers para HTTP, DTOs para entrada e saída, services para regras de negócio, repositories para acesso a dados e entities para persistência.
- Siga o estilo já usado no projeto; mantenha entidades fora da resposta da API e converta-as para DTOs.
- Use injeção por construtor e os recursos de Jakarta/Spring já adotados. Evite adicionar dependências sem necessidade.

## API e validação

- Valide entradas com Jakarta Bean Validation nos DTOs e `@Valid` nos controllers. Não confie apenas na validação do cliente.
- Mantenha controllers focados em HTTP e deixe regras de negócio e transações na camada de service.
- Preserve os contratos HTTP existentes, incluindo status codes, formato das respostas e tratamento de recursos inexistentes.
- Para listagens que possam crescer, prefira paginação a carregar todos os registros sem limite.

## PostgreSQL e persistência

- Configure URL, usuário e senha por propriedades externalizadas/variáveis de ambiente. Nunca versione credenciais nem as inclua em logs.
- Use constraints no banco — como `NOT NULL`, `UNIQUE` e chaves estrangeiras — para garantir integridade também fora da aplicação. Crie índices conforme consultas reais e confirme que não duplicam índices de constraints.
- Nunca use `spring.jpa.hibernate.ddl-auto=update` como estratégia de evolução de schema em ambientes compartilhados ou de produção. Para mudanças de schema persistente, use migrations versionadas (por exemplo, Flyway), mantenha o schema validável e atualize configuração, testes e documentação no mesmo conjunto de mudanças.
- Delimite operações de escrita com `@Transactional` na camada de service; use transações somente leitura para consultas quando apropriado.
- Mantenha associações JPA lazy quando adequado. Planeje o carregamento necessário para cada consulta e evite N+1 e serialização direta de entidades.
- Use parâmetros vinculados em consultas; não concatene valores de entrada em SQL ou JPQL.

## Testes e comandos

- Cubra mudanças com testes relevantes e execute `mvn test`.
- Os testes atuais usam H2 em memória. Ao alterar migrations, SQL, constraints ou comportamento específico do PostgreSQL, valide também com PostgreSQL; não presuma que H2 reproduz todas as suas particularidades.
- Use `docker compose up -d` para iniciar o PostgreSQL local conforme o `README.md`; defina `DATABASE_PASSWORD` no ambiente e nunca a compartilhe.
- Mantenha o `README.md` alinhado quando mudar configuração, execução, persistência ou testes.
