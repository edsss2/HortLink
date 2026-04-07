<div align="center">
  <img src="public/logo_hortlink.png" alt="HortiLink Logo" width="400" />
  <p><strong>Core API: Conectando produtores rurais a consumidores e mini-mercados.</strong></p>

  <p>
    <a href="#-sobre-a-api">Sobre</a> •
    <a href="#-tecnologias">Tecnologias</a> •
    <a href="#-funcionalidades">Funcionalidades</a> •
    <a href="#-documentação">Documentação</a>
  </p>

  <p align="center">
    <img src="https://img.shields.io/badge/Status-Concluído-brightgreen?style=for-the-badge" alt="Status">
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
    <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot">
    <img src="https://img.shields.io/badge/H2_Database-003B57?style=for-the-badge&logo=h2&logoColor=white" alt="H2">
  </p>
</div>

---

## 🚀 Sobre a API

Este repositório contém o **Back-end** do projeto HortiLink. Desenvolvida em Spring Boot, esta API robusta gerencia todas as regras de negócio, persistência de dados e segurança necessária para o ecossistema HortiLink (Web e Mobile).

## 🛠 Tecnologias Utilizadas

- **Java 17+** & **Spring Boot 3**
- **Spring Security + JWT**: Autenticação e autorização via tokens.
- **JPA / Hibernate**: Persistência de dados e mapeamento ORM.
- **H2 Database**: Banco de dados em memória para agilidade no desenvolvimento.
- **Swagger (SpringDoc)**: Documentação interativa dos endpoints.

## 📦 Funcionalidades do Core

- [x] **Gestão de Usuários:** Cadastro de produtores e consumidores com perfis distintos.
- [x] **Segurança JWT:** Proteção de rotas e expiração de tokens.
- [x] **CRUD de Produtos:** Gestão completa do catálogo de hortifrutis.
- [x] **Upload de Imagens:** Processamento e armazenamento de fotos dos produtos.
- [x] **Lógica de Dashboard:** Agregação de dados para métricas de vendas.

## 📖 Documentação (Swagger)

Com a aplicação rodando localmente, acesse a documentação interativa para testar os endpoints:
`http://localhost:8081/swagger-ui.html`

## 📋 Pré-requisitos
* **Java 17** ou superior.
* **Maven** (gerenciador de dependências).
* **IDE** (IntelliJ, VS Code ou Eclipse).

## ⚙️ Como executar
1. Clone este repositório.
2. Certifique-se de que o **Front-end** [Hortlink-frontend](https://github.com/edsss2/HortLink-frontend) está configurado.
3. No terminal da raiz do projeto, execute: `mvn spring-boot:run`.
4. A API estará disponível em: `http://localhost:8081`.

## 🏗️ Arquitetura & Decisões
- **Organização:** Arquitetura em camadas (Controller, Service, Repository, DTO).
- **Padrões:** Uso de *Repository Pattern*, *Singleton* (Spring) e *DTOs* para tráfego de dados.
- **Decisões:** Autenticação **Stateless (JWT)** para integração futura com Mobile e **Banco H2** para facilitar o setup inicial.

## 📦 Tecnologias & Funcionalidades
- **Stack:** Java 17, Spring Boot 3, Spring Security, JPA/Hibernate, H2.
- **Destaques:** Auth JWT, Upload de Imagens e Documentação Swagger.