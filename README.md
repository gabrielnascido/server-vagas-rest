# 📚 Server Vagas REST

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?style=for-the-badge&logo=spring-boot)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Active-success?style=for-the-badge)

## 📖 Descrição do Projeto

**API REST para gerenciamento de vagas de emprego desenvolvida com Spring Boot**

Este é um sistema backend completo que permite criar, listar, atualizar e deletar vagas de emprego. A aplicação oferece funcionalidades robustas de autenticação e autorização, permitindo que empresas publiquem vagas e candidatos se candidatem a oportunidades de forma segura e eficiente.

### 🔗 Integração com Frontend

Este backend funciona em conjunto com o [**client-vagas**](https://github.com/gabrielnascido/client-vagas), uma aplicação frontend que consome esta API. A configuração de CORS está habilitada para permitir requisições do frontend durante o desenvolvimento.

---

## 🚀 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.6** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security + JWT** - Autenticação e autorização
- **H2 Database** - Banco de dados em memória/arquivo para desenvolvimento
- **Lombok** - Redução de código boilerplate
- **Maven** - Gerenciamento de dependências e build
- **Bean Validation** - Validação de dados

---

## ✨ Funcionalidades

- ✅ **CRUD completo de vagas** - Criação, leitura, atualização e exclusão
- ✅ **Autenticação e autorização com JWT** - Sistema seguro de tokens
- ✅ **Validação de dados** - Validações automáticas com Bean Validation
- ✅ **Banco de dados H2** - Ambiente de desenvolvimento com console web
- ✅ **API RESTful** - Seguindo boas práticas e padrões REST
- ✅ **Gerenciamento de usuários** - Candidatos e empresas
- ✅ **Sistema de candidaturas** - Permite candidatos se aplicarem às vagas
- ✅ **Controle de acesso** - Permissões baseadas em roles

---

## 📡 Endpoints da API

### 🔐 Autenticação

```http
POST /login - Login de usuário/empresa
POST /logout - Logout (requer autenticação)
```

### 💼 Vagas (Jobs)

```http
GET /jobs/{id} - Buscar vaga por ID
POST /jobs - Criar nova vaga (requer autenticação - empresa)
POST /jobs/search - Buscar vagas com filtros
PATCH /jobs/{id} - Atualizar vaga (requer autenticação - empresa)
DELETE /jobs/{id} - Deletar vaga (requer autenticação - empresa)
POST /jobs/{id} - Candidatar-se a uma vaga (requer autenticação - candidato)
POST /jobs/{id}/feedback - Enviar feedback ao candidato (empresa)
```

### 👤 Usuários (Candidatos)

```http
POST /users - Criar novo candidato
GET /users/{user_id} - Buscar candidato por ID
PATCH /users/{user_id} - Atualizar dados do candidato
DELETE /users/{user_id} - Deletar candidato
GET /users/{user_id}/jobs - Listar candidaturas do usuário
```

### 🏢 Empresas

```http
POST /companies - Criar nova empresa
GET /companies/{company_id} - Buscar empresa por ID
PATCH /companies/{company_id} - Atualizar dados da empresa
DELETE /companies/{company_id} - Deletar empresa
POST /companies/{company_id}/jobs - Listar vagas da empresa
GET /companies/{company_id}/jobs/{job_id} - Listar candidatos de uma vaga
```

### ⚙️ Administração

```http
GET /dashboard.html - Dashboard administrativo
GET /admin/dashboard/logs - Logs de requisições HTTP
GET /admin/dashboard/users - Usuários logados no sistema
```

---

## 🛠️ Como Executar o Projeto

### 📋 Pré-requisitos

```bash
- Java 17 ou superior
- Maven 3.6+
```

### 💻 Instalação e Execução

#### Opção 1: Usando Maven Wrapper (Recomendado)

```bash
# Clone o repositório
git clone https://github.com/gabrielnascido/server-vagas-rest.git

# Entre no diretório
cd server-vagas-rest

# Execute com Maven Wrapper (Linux/Mac)
./mvnw spring-boot:run

# Execute com Maven Wrapper (Windows)
mvnw.cmd spring-boot:run
```

#### Opção 2: Compilar e executar o JAR

```bash
# Compile o projeto
./mvnw clean package

# Execute o arquivo JAR gerado
java -jar target/server-vagas-rest-0.0.1-SNAPSHOT.jar
```

O servidor estará disponível em: **http://localhost:8080**

---

## ⚙️ Configuração

### 🗄️ Banco de Dados H2

O banco de dados H2 está configurado e pode ser acessado através do console web:

```
URL Console: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:./data/testdb
Username: sa
Password: password
```

### 🔑 JWT Configuration

- **Expiração do Token**: 1 hora (3600000 ms)
- A chave secreta JWT está configurada em `application.properties`
- Sistema de blacklist para tokens revogados

### 🌐 CORS

CORS está configurado para permitir requisições do frontend durante o desenvolvimento.

---

## 📂 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/example/servervagasrest/
│   │   ├── config/          # Configurações (Security, CORS, JWT)
│   │   ├── controller/      # Controllers REST
│   │   │   └── dto/         # Data Transfer Objects
│   │   ├── model/           # Entidades JPA (User, Job, Company, etc)
│   │   ├── repository/      # Repositories (Spring Data JPA)
│   │   ├── service/         # Lógica de negócio
│   │   └── exception/       # Tratamento de exceções
│   └── resources/
│       ├── application.properties  # Configurações da aplicação
│       ├── ValidationMessages.properties
│       └── static/          # Arquivos estáticos (dashboard)
└── test/
    └── java/                # Testes unitários e de integração
```

---

## 🔄 Integração com Frontend

Este backend foi projetado para funcionar perfeitamente com o [**client-vagas**](https://github.com/gabrielnascido/client-vagas).

### Configurações importantes:

- **Porta padrão do backend**: 8080
- **CORS**: Configurado para aceitar requisições do frontend
- **Formato de comunicação**: JSON
- **Autenticação**: JWT Token via header `Authorization: Bearer <token>`

Para integrar com o frontend:

1. Clone e configure o [client-vagas](https://github.com/gabrielnascido/client-vagas)
2. Configure a URL da API no frontend para apontar para `http://localhost:8080`
3. Execute ambos os projetos simultaneamente

---

## 🛠️ Build para Produção

```bash
# Compilar o projeto
./mvnw clean package

# O arquivo JAR será gerado em:
# target/server-vagas-rest-0.0.1-SNAPSHOT.jar

# Executar em produção
java -jar target/server-vagas-rest-0.0.1-SNAPSHOT.jar
```

---

## 🔐 Segurança

- ✅ **Autenticação via JWT** - Tokens seguros e stateless
- ✅ **Spring Security** - Proteção de endpoints
- ✅ **Validação de dados** - Previne entrada de dados inválidos
- ✅ **Blacklist de tokens** - Controle de tokens revogados
- ✅ **Senhas criptografadas** - BCrypt para hash de senhas
- ✅ **CORS configurado** - Controle de origens permitidas

---

## 👨‍💻 Desenvolvedor

**Desenvolvido por Gabriel Nascido**

- GitHub: [@gabrielnascido](https://github.com/gabrielnascido)
- LinkedIn: [Seu LinkedIn]

---

## 📄 Licença

Este projeto está sob a licença **MIT**.

---

## 📝 Notas

- Certifique-se de que a porta 8080 não esteja em uso por outro aplicativo
- Em produção, configure um banco de dados apropriado (PostgreSQL, MySQL, etc)
- Altere a chave secreta JWT em ambientes de produção
- Configure variáveis de ambiente para dados sensíveis

---

**⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!**
