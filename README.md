# Appointment Stream

Sistema de gerenciamento de agendamentos baseado em microserviços.

## Visão Geral

O Appointment Stream é uma aplicação distribuída desenvolvida em Java utilizando Spring Boot 3.2.12, projetada para gerenciar agendamentos de forma eficiente e escalável.

## Arquitetura

O sistema é composto pelos seguintes microserviços:

- **appointment-service**: Gerenciamento principal de agendamentos
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
- Docker & Docker Compose
- PostgreSQL 15
- MongoDB
- Mongo Express

## Estrutura do Projeto

```
appointment-stream/
├── apps/                           # Diretório contendo os microserviços
│   ├── appointment-service/        # Serviço de agendamentos
│   ├── auth-service/              # Serviço de autenticação
│   ├── notification-service/      # Serviço de notificações
│   ├── history-service/          # Serviço de histórico
│   └── user-service/             # Serviço de usuários
├── .mvn/                         # Configurações do Maven Wrapper
├── .vscode/                      # Configurações do VS Code
├── docker-compose.yml            # Configuração do Docker Compose
└── pom.xml                       # Configuração principal do Maven
```

## Requisitos

- Java 21
- Maven 3.6+
- Git
- Docker
- Docker Compose

## Configuração do Ambiente

1. Clone o repositório
2. Certifique-se de ter o Java 21 instalado
3. Execute `mvn clean install` na raiz do projeto

### Configuração do Docker

O projeto utiliza Docker para gerenciar os serviços de banco de dados. Para iniciar os containers, execute:

```bash
docker-compose up -d
```

Isso iniciará os seguintes serviços:
- PostgreSQL na porta 5432
- MongoDB na porta 27017
- Mongo Express (interface web do MongoDB) na porta 8081

Para parar os containers:
```bash
docker-compose down
```

## Desenvolvimento

Cada microserviço pode ser desenvolvido e executado independentemente. Para executar um serviço específico, navegue até o diretório do serviço e execute:

```bash
mvn spring-boot:run
```

## Licença

Este projeto é privado e confidencial.

