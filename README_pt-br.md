# Sistema de Gerenciamento de Usuários

Um sistema de gerenciamento de usuários nativo da nuvem construído com **Spring Boot 4.0.2**, **Thymeleaf**, **HTMX** e **PostgreSQL**.

## Pré-requisitos

-   **Java 25** (Recomendado Eclipse Temurin)
-   **Docker** & **Docker Compose**
-   **Git**

## Começando

Siga estas instruções para rodar o projeto na sua máquina local.

### 1. Clonar o Repositório

```bash
git clone https://github.com/jonasdurau/usermanagement.git
cd usermanagement
```

### 2. Configuração do Banco de Dados

Usamos o **Docker Compose** para subir um banco de dados PostgreSQL e um container pgAdmin.

```bash
docker compose up -d
```

Isso iniciará:
-   **PostgreSQL**: Porta `5432`
-   **pgAdmin**: Porta `5050` (Login: `admin@admin.com` / `admin`)
-   **Banco de Dados**: `usermanagement`
-   **Usuário/Senha**: `jonas` / `password123`

### 3. Rodar a Aplicação

Você pode rodar a aplicação diretamente usando o Gradle Wrapper:

```bash
./gradlew bootRun
```

A aplicação iniciará em `http://localhost:8080`.

**Nota**: As migrações do banco de dados (Flyway) rodarão automaticamente na inicialização.

### 4. Docker (Modo Produção)

Para rodar toda a stack (Banco + Backend) via Docker:

```bash
docker compose up --build
```
Isso constrói a imagem da aplicação usando um Dockerfile de múltiplos estágios baseado no `eclipse-temurin:25-jdk`.

## Funcionalidades

-   **Operações CRUD**: Criar, Ler, Atualizar, Deletar usuários.
-   **Integração HTMX**: Busca dinâmica e remoção sem recarregar a página inteira.
-   **Interface Responsiva**: Estilizada com Bootstrap e CSS customizado.
-   **Migrações de Banco**: Gerenciadas pelo Flyway.
