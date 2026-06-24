# API Banco Digital - Teste Técnico Compass UOL

Este repositório contém a minha solução para o desafio técnico da Compass UOL.

O objetivo do projeto foi construir uma API REST para um banco digital, focando em operações de transferência de valores e extrato.

## Tecnologias e Ferramentas

stack:

- **Java 21** e **Spring Boot 3.5**.
- **H2 Database** rodando em memória
- **Spring Data JPA** com **Hibernate**.
- **Swagger (OpenAPI)**.
- **JUnit 5 e Mockito**.
- **Maven**.

## Como rodar o projeto localmente
testar a aplicação. Siga os passos abaixo:

1. Clone este repositório:
   `git clone <https://github.com/emefsilva/compass-uol.git>`
2. Pelo terminal, na raiz do projeto, rode o comando:
   `./mvnw spring-boot:run`
3. A aplicação vai subir na porta 8080. O banco de dados já inicia com algumas contas carregadas automaticamente (IDs 1, 2 e 3).

**Links úteis para testes:**
-  **Swagger (Interface da API):** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- ️ **Console do Banco H2:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console) *(URL `jdbc:h2:mem:bancodigitaldb`)*

## Endpoints Disponíveis
A documentação completa com os schemas está no Swagger, mas um resumo do que a API faz:

* **`/accounts`:** Endpoints para criar novas contas (POST), listar os contas cadastrados (GET), listar contas por ID (GET), deletar contar por ID (DELETE)
* **`/transfers`:** Aqui realiza as transferências de valores (POST) e consulta o histórico completo de movimentações (GET).

## Decisões de Arquitetura

O projeto foi estruturado seguindo o padrão de **Arquitetura em Camadas**, foram tomadas as seguintes decisões:

- **Concorrência e Consistência:** Para evitar que duas transferências ocorrem ao mesmo tempo e o saldo fica inconsistente, utilizei a estratégia de **Pessimistic Lock** (`@Lock(LockModeType.PESSIMISTIC_WRITE)`) direto no Repository. para travar a linha da conta no banco durante a transação, garantindo que o dinheiro não evapore nem se duplique.
- **Performance com Async:** O requisito pedia o envio de uma notificação após a transferência, isolei essa responsabilidade em um serviço separado e usei a anotação `@Async`. Assim, a transferência é concluída na mesma hora e a notificação roda em segundo plano.
- **Segurança na Entrada (DTOs):** Utilizei `records` do Java para manter a imutabilidade dos dados e apliquei validações (`@NotNull`, `@Positive`) direto na porta de entrada da API. Se tentar transferir R$ 0 ou um valor negativo, a requisição já é barrada no Controller com um HTTP 400.
- **Testes:** Utilizei junit e Mockito para mocar os dados e testar a camada de serviço
