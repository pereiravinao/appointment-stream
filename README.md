# Appointment Stream

Sistema de gerenciamento de agendamentos baseado em microserviços.

## Visão Geral

O Appointment Stream é uma aplicação distribuída desenvolvida em Java utilizando Spring Boot 3.2.12, projetada para gerenciar agendamentos de forma eficiente e escalável.

## Arquitetura

O sistema é composto pelos seguintes microserviços:

- **appointment-service**: Gerenciamento principal de agendamentos
  - API GraphQL para consultas e mutações de agendamentos
  - Suporte a queries e mutations
  - Validação de schemas GraphQL
- **auth-service**: Autenticação e autorização
- **notification-service**: Serviço de notificações
- **history-service**: Registro de histórico
- **user-service**: Gerenciamento de usuários

## Tecnologias

- Java 21
- Spring Boot 3.2.12
- Maven
- Lombok
- Spring Boot Dependencies
- GraphQL
- Docker & Docker Compose
- PostgreSQL 15
- MongoDB
- Mongo Express
- RabbitMQ

## Estrutura do Projeto

```
appointment-stream/
├── apps/                           # Diretório contendo os microserviços
│   ├── appointment-service/        # Serviço de agendamentos
│   ├── auth-service/              # Serviço de autenticação
│   ├── notification-service/      # Serviço de notificações
│   ├── history-service/          # Serviço de histórico
│   └── user-service/             # Serviço de usuários
├── common/                        # Módulos comuns compartilhados
│   ├── auth-common/              # Componentes comuns de autenticação
│   ├── base-common/              # Componentes base comuns
│   ├── message-common/           # Componentes comuns de mensageria
│   ├── user-common/              # Componentes comuns de usuário
│   └── utils-common/             # Utilitários comuns
├── docker-compose.yml            # Configuração do Docker Compose
└── pom.xml                       # Configuração principal do Maven
```

## Requisitos

- Java 21
- Maven 3.6+
- Git
- Docker
- Docker Compose
- RabbitMQ

## Configuração do Ambiente

1. Clone o repositório
2. Certifique-se de ter o Java 21 instalado
3. Execute `mvn clean install` na raiz do projeto

### Configuração do Docker

O projeto utiliza Docker para gerenciar os serviços de banco de dados e mensageria. Para iniciar os containers, execute:

```bash
docker-compose up -d
```

Isso iniciará os seguintes serviços:
- PostgreSQL na porta 5432
- MongoDB na porta 27017
- Mongo Express (interface web do MongoDB) na porta 8081
- RabbitMQ na porta 5672 (AMQP) e 15672 (interface web)

Para parar os containers:
```bash
docker-compose down
```

## Funcionalidades

### Appointment Service
- Gerenciamento completo de agendamentos
- API GraphQL para consultas e mutações
- Integração com RabbitMQ para eventos de agendamento
- Validação de schemas GraphQL

### Notification Service
- Processamento de eventos de agendamento
- Notificações em tempo real
- Integração com RabbitMQ para recebimento de eventos

### User Service
- Gerenciamento de usuários
- Autenticação e autorização
- Endpoints para detalhes de usuário
- Integração com outros serviços via Feign Client

## Desenvolvimento

Cada microserviço pode ser desenvolvido e executado independentemente. Para executar um serviço específico, navegue até o diretório do serviço e execute:

```bash
mvn spring-boot:run
```

## Licença

Este projeto é privado e confidencial.

