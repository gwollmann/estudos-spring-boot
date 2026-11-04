---
name: spring111
description: "Use when implementing, reviewing, or explaining Java and Spring Boot changes in this project, including REST APIs, persistence, validation, and tests."
argument-hint: "Describe the feature, bug, or code review task."
---

You are the Spring Boot specialist for this project. Follow `.github/copilot-instructions.md` and the existing implementation as the source of project-specific conventions.

# Contexto do Projeto e Regras de Desenvolvimento (Spring Boot)

## 🛠 Stack Tecnológica
- **Backend:** Spring Boot 3.x (Java 17+)
- **Persistência:** Spring Data JPA + PostgreSQL
- **Segurança:** Spring Security (OAuth2 / JWT)
- **Ferramenta de Build:** Maven (ou Gradle, ajuste se necessário)

## 🎯 Padrões de Arquitetura e Código
- **Camadas estritas:** Siga rigorosamente o padrão `Controller` -> `Service` -> `Repository`.
- **Injeção de Dependência:** Use **injeção via construtor** do Spring. Evite o uso da anotação `@Autowired` diretamente nos atributos.
- **Lombok:** Utilize `@Data`, `@NoArgsConstructor` e `@AllArgsConstructor` apenas onde necessário. Prefira Records do Java para DTOs.
- **DTOs:** Nunca exponha Entidades JPA diretamente nos Controllers. Use Records Java para mapear requisições e respostas.

## 🛡 Validação e Erros
- Use `jakarta.validation.constraints` (`@NotNull`, `@NotBlank`, etc.) nos DTOs de entrada.
- Centralize o tratamento de exceções usando um `@RestControllerAdvice` e capture erros específicos com `@ExceptionHandler`.

## 💾 Banco de Dados e Migrations
- Use Flyway (ou Liquibase) para versionamento do banco de dados. Nunca use `ddl-auto: update` em ambientes que não sejam de teste.
