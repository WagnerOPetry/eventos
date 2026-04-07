# 📅 Eventos - Sistema de Gestão de Eventos

[![Java 21](https://img.shields.io/badge/Java-21-red?style=flat-square&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot 3.3](https://img.shields.io/badge/Spring%20Boot-3.3-green?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![Angular 15](https://img.shields.io/badge/Angular-15-red?style=flat-square&logo=angular)](https://angular.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=flat-square&logo=postgresql)](https://www.postgresql.org/)

Uma aplicação full-stack de **gestão de eventos** com arquitetura moderna, testes automatizados e pronta para produção.

## 🎯 Visão Geral

Este projeto implementa um sistema escalável para criar, consultar, atualizar e deletar eventos com:
- ✅ API REST com documentação Swagger/OpenAPI
- ✅ Frontend responsivo com Angular
- ✅ Validação de dados em camadas
- ✅ Banco de dados com migrations automáticas (Flyway)
- ✅ Soft delete implementado
- ✅ Testes unitários e de integração
- ✅ Containerização com Docker/Podman
- ✅ Suporte a diferentes ambientes (H2, PostgreSQL)

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────────┐
│  Frontend (Angular 15)                          │
│  - Components, Serviços, Routing, Bootstrap UI  │
└──────────────────┬──────────────────────────────┘
                   │ HTTP/REST
┌──────────────────▼──────────────────────────────┐
│  Backend (Spring Boot 3.3)                      │
│  ┌──────────────────────────────────────────┐  │
│  │ Controller Layer                         │  │
│  │ └─ EventController (REST endpoints)      │  │
│  └──────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────┐  │
│  │ Service Layer                            │  │
│  │ └─ EventService (lógica de negócio)      │  │
│  └──────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────┐  │
│  │ Repository Layer (JPA)                   │  │
│  │ └─ EventRepository (acesso a dados)      │  │
│  └──────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────┐  │
│  │ Domain Models & DTOs                     │  │
│  │ └─ Event, EventRequest, EventResponse    │  │
│  └──────────────────────────────────────────┘  │
└──────────────────┬──────────────────────────────┘
                   │ JDBC/SQL
┌──────────────────▼──────────────────────────────┐
│  PostgreSQL 15 (Produção) / H2 (Dev/Testes)    │
└─────────────────────────────────────────────────┘
```

---

## 📋 Pré-requisitos

### Para desenvolvimento local
- **JDK 21** ([download](https://www.oracle.com/java/technologies/downloads/#java21))
- **Maven 3.8+** (incluído via `mvnw`)
- **Node.js 18+** ([download](https://nodejs.org/))
- **Git**

### Para containerização
- **Docker 20.10+** e **Docker Compose 2.0+** OU
- **Podman 4.0+** e **Podman Compose**

---

## 🚀 Iniciando Rápido

### 1️⃣ Clone o repositório
```bash
git clone <repository-url>
cd eventos
```

### 2️⃣ Desenvolver localmente

#### Backend
```bash
# Build
./mvnw clean package -DskipTests

# Rodar testes
./mvnw test

# Executar aplicação (H2 em memória por padrão)
./mvnw spring-boot:run
```
A API estará em: **http://localhost:8080/api/events**  
Swagger UI: **http://localhost:8080/swagger-ui.html**

#### Frontend
```bash
cd frontend

# Instalar dependências
npm install

# Desenvolvimento (proxy para backend em localhost:8080)
npm start

# Build de produção
npm run build
```
A aplicação estará em: **http://localhost:4200**

### 3️⃣ Rodar com Docker Compose (stack completa)

#### Com Docker
```bash
# Criar arquivo .env com credenciais (veja .env.example)
docker compose up --build
```

#### Com Podman
```bash
podman-compose up --build
```

**Serviços disponíveis:**
- Backend: http://localhost:8080
- Frontend: http://localhost
- PostgreSQL: localhost:5432 (interno)

---

## 📊 Endpoints da API

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `GET` | `/api/events` | Listar eventos (paginado) | ✅ |
| `GET` | `/api/events/{id}` | Obter evento por ID | ✅ |
| `POST` | `/api/events` | Criar novo evento | ✅ |
| `PUT` | `/api/events/{id}` | Atualizar evento | ✅ |
| `DELETE` | `/api/events/{id}` | Deletar evento (soft delete) | ✅ |

### Exemplo de uso (cURL)
```bash
# Listar eventos (página 0, 10 itens)
curl -X GET "http://localhost:8080/api/events?page=0&size=10"

# Criar evento
curl -X POST "http://localhost:8080/api/events" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Conferência Tech 2024",
    "description": "Palestra sobre inovação",
    "eventDate": "2024-12-15T10:00:00",
    "location": "São Paulo"
  }'

# Obter evento específico
curl -X GET "http://localhost:8080/api/events/1"
```

---

## 🗄️ Banco de Dados

### Schema (Flyway V1)
```sql
CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    event_date TIMESTAMP NOT NULL,
    location VARCHAR(255),
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Migrations
- Migrations Flyway localizadas em: `src/main/resources/db/migration/`
- Executadas automaticamente no startup da aplicação
- Suporte a versionamento incremental

### Configuração de banco

**Development (H2 em memória):**
```properties
spring.datasource.url=jdbc:h2:mem:eventosdb
spring.datasource.driver-class-name=org.h2.Driver
```

**Produção (PostgreSQL):**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/eventos_db
spring.datasource.username=postgres
spring.datasource.password=seu_password
spring.datasource.driver-class-name=org.postgresql.Driver
```

---

## 🧪 Testes

### Executar todos os testes
```bash
./mvnw test
```

### Executar testes de integração
```bash
./mvnw test -Dtest=*IntegrationTest
```

### Executar testes unitários específicos
```bash
./mvnw test -Dtest=EventServiceImplTest
```

### Cobertura de testes
```bash
./mvnw clean test jacoco:report
# Relatório em: target/site/jacoco/index.html
```

---

## 📦 Dependências Principais

### Backend
| Dependência | Versão | Propósito |
|-------------|--------|----------|
| Spring Boot | 3.3.0 | Framework principal |
| Spring Data JPA | - | ORM e acesso a dados |
| PostgreSQL Driver | - | Conexão com PostgreSQL |
| Flyway | 10.0+ | Migrations de banco |
| Lombok | - | Redução de boilerplate |
| Jakarta Validation | - | Validação de dados |
| Springdoc OpenAPI | 2.6.0 | Swagger/OpenAPI |
| H2 Database | - | Testes em memória |

### Frontend
| Dependência | Versão | Propósito |
|-------------|--------|----------|
| Angular | 15.2.0 | Framework frontend |
| Bootstrap | 5.2.3 | UI/CSS Framework |
| ngx-pagination | 6.0.2 | Paginação |
| TypeScript | 4.9.5 | Tipagem estática |

---

## 🔧 Configuração

### Variáveis de Ambiente
Crie um arquivo `.env` na raiz:
```env
# PostgreSQL
POSTGRES_USER=postgres
POSTGRES_PASSWORD=sua_senha_segura
POSTGRES_DB=eventos_db

# Spring Boot (opcional, Docker detecta automaticamente)
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/eventos_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=sua_senha_segura
```

### application.properties
Arquivo: `src/main/resources/application.properties`
- Configurações padrão com H2
- Sobrescrito por variáveis de ambiente ou Docker
- Swagger/OpenAPI pré-configurado

---

## 📁 Estrutura do Projeto

```
eventos/
├── src/
│   ├── main/
│   │   ├── java/com/desafio/eventos/
│   │   │   ├── controller/        # REST Controllers
│   │   │   ├── service/           # Lógica de negócio
│   │   │   ├── repository/        # Acesso a dados (JPA)
│   │   │   ├── domain/            # Entidades JPA
│   │   │   ├── dto/               # Data Transfer Objects
│   │   │   ├── config/            # Configurações (OpenAPI, CORS, etc)
│   │   │   ├── exception/         # Tratamento de exceções
│   │   │   └── EventosApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/      # Scripts Flyway
│   └── test/
│       └── java/com/desafio/eventos/
│           ├── service/           # Testes unitários
│           └── EventosIntegrationTest.java
├── frontend/                        # Aplicação Angular
│   ├── src/
│   │   ├── app/
│   │   │   ├── events/            # Feature module
│   │   │   └── app.module.ts
│   │   └── main.ts
│   ├── package.json
│   ├── angular.json
│   └── Dockerfile
├── docker-compose.yml             # Stack completa
├── Dockerfile                     # Backend Docker
├── pom.xml                        # Maven POM
└── README.md
```

---

## 🔐 Recursos Implementados

- ✅ **Soft Delete**: Eventos não são removidos, apenas marcados como deletados
- ✅ **Validações**: Bean Validation (Jakarta) em DTOs
- ✅ **Paginação**: Suporte a Page/Pageable do Spring Data
- ✅ **DTOs**: Separação entre domain e API
- ✅ **Exception Handling**: Tratamento centralizado de exceções
- ✅ **CORS**: Configurado para desenvolvimento
- ✅ **OpenAPI/Swagger**: Documentação interativa
- ✅ **Migrations**: Versionamento de schema com Flyway

---

## 🐛 Troubleshooting

### Porta 8080 já em uso
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### Erro de conexão com PostgreSQL (Docker)
```bash
# Verificar containers
docker ps
docker compose logs db

# Rebuild
docker compose down -v
docker compose up --build
```

### Node modules corrompidos
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

---

## 📝 Boas Práticas

1. **Sempre rodar testes antes de commit**: `./mvnw test`
2. **Atualizar migrations** em: `src/main/resources/db/migration/`
3. **Validar com Swagger** antes de integração: `http://localhost:8080/swagger-ui.html`
4. **Usar DTOs** para requests/responses (nunca expor entidades)
5. **Implementar soft delete** para dados críticos
6. **Manter `.env` fora do versionamento** (adicionar ao `.gitignore`)

---

## 🚢 Deploy

### Build para produção
```bash
# Backend
./mvnw clean package -DskipTests -Pprod

# Frontend
cd frontend && npm run build
```

### Imagem Docker
```bash
# Backend
docker build -t eventos-backend:1.0 .

# Frontend
docker build -t eventos-frontend:1.0 -f frontend/Dockerfile frontend

# Push para registry
docker tag eventos-backend:1.0 seu-registry/eventos-backend:1.0
docker push seu-registry/eventos-backend:1.0
```

---

## 📚 Documentação Adicional

- [Swagger/OpenAPI](http://localhost:8080/swagger-ui.html) - Documentação interativa da API
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Angular Docs](https://angular.io/docs)

---

## 👨‍💻 Desenvolvimento

### Adicionar nova dependência Maven
```bash
./mvnw dependency:tree  # Ver árvore de dependências
```

### Debugar aplicação
```bash
# Backend com debug
./mvnw spring-boot:run -Dspring-boot.run.arguments="--debug"

# Frontend com debug
ng serve --poll=2000
```

## ✨ Contribuidores

Wagner Petry

---

**Última atualização**: 2024  
**Versão**: 1.0.0-SNAPSHOT
