# Documentação do Sistema Appointment Stream

## Visão Geral do Projeto

Appointment Stream é um sistema de gerenciamento de agendamentos baseado em microserviços, desenvolvido em Java utilizando Spring Boot 3.2.12. O sistema é projetado para gerenciar agendamentos de forma eficiente e escalável, seguindo uma arquitetura moderna de microserviços.

## Arquitetura do Sistema

O sistema segue uma arquitetura de microserviços, onde cada serviço é responsável por uma funcionalidade específica:

### Microserviços

1. **Appointment Service** (Porta: 8083)
   - Gerenciamento principal de agendamentos
   - API GraphQL para consultas e mutações
   - Responsável pelo CRUD de agendamentos

2. **Auth Service** (Porta: 8080)
   - Autenticação e autorização de usuários
   - Gestão de tokens JWT
   - Login, logout e registro de usuários

3. **User Service** (Porta: 8082)
   - Gerenciamento de informações de usuários
   - Fornece endpoints para consulta e atualização de usuários
   - Integração com outros serviços via Feign Client

4. **Notification Service** (Porta: 8085)
   - Processamento de eventos de agendamento
   - Envio de notificações em tempo real
   - Integração com RabbitMQ para recebimento de eventos

5. **History Service** (Porta: 8087)
   - Registro de histórico de ações no sistema
   - Rastreamento de alterações em agendamentos

### Comunicação entre Serviços

- **Comunicação Síncrona**: REST API entre serviços para requisições diretas
- **Comunicação Assíncrona**: RabbitMQ para eventos e mensagens assíncronas
- **Service Discovery**: Registro e descoberta de serviços

### Processamento de Eventos via RabbitMQ

O sistema utiliza RabbitMQ para comunicação assíncrona entre os microserviços, permitindo desacoplamento e melhor escalabilidade. Os principais fluxos de eventos são:

1. **Eventos de Agendamento**:
   - Quando um agendamento é criado, atualizado ou excluído, o Appointment Service publica eventos no RabbitMQ
   - O Notification Service e o History Service consomem esses eventos para enviar notificações e registrar histórico, respectivamente

2. **Eventos de Usuário**:
   - Ações como criação de conta e atualização de perfil geram eventos do User Service
   - Esses eventos são processados por outros serviços interessados nas mudanças de dados do usuário

### Estrutura de Filas e Exchanges do RabbitMQ

O sistema utiliza o modelo de troca de tópicos (Topic Exchange) do RabbitMQ para rotear mensagens de maneira flexível:

- **Exchanges**:
  - `appointment-exchange`: Usado para eventos relacionados a agendamentos
  - `user-exchange`: Usado para eventos relacionados a usuários
  - `notification-exchange`: Usado para eventos de notificação

- **Filas**:
  - `appointment.notification.queue`: Consome eventos de agendamento para gerar notificações
  - `appointment.history.queue`: Consome eventos de agendamento para registro histórico
  - `user.notification.queue`: Consome eventos de usuário para notificações
  - `user.history.queue`: Consome eventos de usuário para registro histórico

- **Padrões de Routing Keys**:
  - `appointment.created`: Evento de criação de agendamento
  - `appointment.updated`: Evento de atualização de agendamento
  - `appointment.deleted`: Evento de exclusão de agendamento
  - `user.registered`: Evento de registro de usuário
  - `user.updated`: Evento de atualização de usuário

## Tecnologias Utilizadas

- **Backend**:
  - Java 21
  - Spring Boot 3.2.12
  - Spring Security
  - Spring Data JPA
  - GraphQL (para o Appointment Service)
  - Lombok
  - JWT (JSON Web Tokens)

- **Banco de Dados**:
  - PostgreSQL 15 (dados relacionais)
  - MongoDB (armazenamento de histórico)

- **Mensageria**:
  - RabbitMQ

- **Ferramentas de Desenvolvimento**:
  - Maven (gerenciamento de dependências)
  - Docker & Docker Compose (containerização)
  - Mongo Express (interface web para MongoDB)

## API Endpoints

### Auth Service (REST API)

- **POST /api/v1/auth/login**
  - Autenticação de usuários
  - Entrada: 
    ```json
    {
      "email": "usuario@exemplo.com", 
      "password": "senha123"
    }
    ```
  - Saída: 
    ```json
    {
      "id": "1",
      "name": "Nome do Usuário",
      "email": "usuario@exemplo.com",
      "roles": ["USER"],
      "token": "eyJhbGciOiJIUzI1NiJ9...",
      "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
    }
    ```
  - Também define cookies HTTP-only para tokens

- **POST /api/v1/auth/register**
  - Registro de novos usuários
  - Entrada:
    ```json
    {
      "name": "Nome do Usuário",
      "email": "usuario@exemplo.com",
      "password": "senha123"
    }
    ```
  - Saída: Similar ao endpoint de login

- **POST /api/v1/auth/logout**
  - Logout de usuários
  - Invalida tokens de acesso
  - Limpa cookies de autenticação

### User Service (REST API)

- **GET /api/v1/users/{authId}**
  - Obter usuário pelo ID de autenticação (uso interno)
  - Saída:
    ```json
    {
      "id": 1,
      "authId": "auth123",
      "name": "Nome do Usuário",
      "email": "usuario@exemplo.com",
      "roles": ["USER"]
    }
    ```

- **GET /api/v1/users/{id}/details**
  - Obter detalhes do usuário por ID (uso interno)
  - Saída: Similar ao endpoint anterior

- **GET /api/v1/users/me**
  - Obter dados do usuário autenticado
  - Requer autenticação
  - Saída: Similar aos endpoints anteriores

- **PUT /api/v1/users/{id}/roles**
  - Atualizar papéis/funções de um usuário
  - Requer papel de ADMIN
  - Entrada:
    ```json
    {
      "roles": ["USER", "ADMIN"]
    }
    ```
  - Saída: Dados atualizados do usuário

- **GET /api/v1/users**
  - Listar todos os usuários (paginado)
  - Requer papel de ADMIN
  - Parâmetros de Query: `page`, `size`, `sort`
  - Saída:
    ```json
    {
      "content": [
        {
          "id": 1,
          "name": "Usuario 1",
          "email": "usuario1@exemplo.com",
          "roles": ["USER"]
        },
        {
          "id": 2,
          "name": "Usuario 2",
          "email": "usuario2@exemplo.com",
          "roles": ["USER", "ADMIN"]
        }
      ],
      "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
          "sorted": true,
          "unsorted": false
        }
      },
      "totalElements": 2,
      "totalPages": 1
    }
    ```

### Appointment Service (GraphQL API)

**Endpoint GraphQL**: `/api/graphql`
**GraphiQL Interface**: `/api/graphiql` (habilitado em ambiente de desenvolvimento)

- **Queries**:
  - `findAll`: Retorna todos os agendamentos
    ```graphql
    query {
      findAll {
        id
        name
        description
        status
        starTime
        endTime
        consumer {
          id
          name
        }
      }
    }
    ```
  
  - `findById(id: ID!)`: Retorna um agendamento específico por ID
    ```graphql
    query {
      findById(id: "1") {
        id
        name
        description
        status
        starTime
        endTime
        type
        consumer {
          id
          name
          email
        }
      }
    }
    ```

- **Mutations**:
  - `create(appointment: AppointmentInput!)`: Cria um novo agendamento
    ```graphql
    mutation {
      create(appointment: {
        name: "Consulta Médica"
        description: "Consulta de rotina"
        status: "SCHEDULED"
        starTime: "2023-06-10T10:00:00"
        endTime: "2023-06-10T11:00:00"
        type: "MEDICAL"
        consumerId: "1"
      }) {
        id
        name
        status
      }
    }
    ```
  
  - `update(appointment: AppointmentUpdate!)`: Atualiza um agendamento existente
    ```graphql
    mutation {
      update(appointment: {
        id: "1"
        description: "Consulta de rotina atualizada"
        status: "CONFIRMED"
      }) {
        id
        name
        status
        description
      }
    }
    ```
  
  - `delete(id: ID!)`: Remove um agendamento
    ```graphql
    mutation {
      delete(id: "1")
    }
    ```

## Estrutura de Dados

### Appointment (Agendamento)

```graphql
type Appointment {
    id: ID!
    name: String!
    description: String
    status: String
    starTime: String
    endTime: String
    type: String
    createdBy: User
    consumer: User
    ownerId: Int
    createdAt: String
    updatedAt: String
    version: Int
}
```

### User (Usuário)

```graphql
type User {
    id: ID!
    name: String
    email: String
}
```

## Configuração do Ambiente

### Requisitos

- Java 21
- Maven 3.6+
- Docker
- Docker Compose

### Passos para Configuração

1. **Clone do Repositório**:
   ```bash
   git clone <repositório>
   cd appointment-stream
   ```

2. **Compilação do Projeto**:
   ```bash
   mvn clean install
   ```

3. **Iniciar Infraestrutura via Docker**:
   ```bash
   docker-compose up -d
   ```
   
   Isso iniciará:
   - PostgreSQL (porta 5432)
   - MongoDB (porta 27017)
   - Mongo Express (porta 8081)
   - RabbitMQ (porta 5672 para AMQP, 15672 para interface web)

4. **Configuração de Banco de Dados**:
   - O sistema está configurado para usar PostgreSQL em `localhost:5432`
   - Nome do banco: `appointment-stream`
   - Usuário: `postgres`
   - Senha: `postgres`

5. **RabbitMQ**:
   - Host: `localhost`
   - Porta: `5672`
   - Usuário: `admin`
   - Senha: `admin`

### Execução dos Serviços

Para executar cada microserviço individualmente:

```bash
cd apps/appointment-service
mvn spring-boot:run
```

Repita o processo para os demais serviços, substituindo `appointment-service` pelo nome do serviço desejado.

## Segurança

- Autenticação baseada em JWT
- Tokens de acesso e refresh
- Cookies HTTP-only e secure para armazenamento de tokens
- Controle de acesso baseado em papéis (RBAC)
- Comunicação interna segura entre serviços

## Monitoramento e Logs

- Os serviços estão configurados para logging detalhado
- Nível de log para Spring Security: DEBUG
- Registros de ações são mantidos no History Service


## Postman Collection

