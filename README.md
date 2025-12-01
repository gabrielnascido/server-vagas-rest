# API de Vagas - Backend

API REST para sistema de gerenciamento de vagas de emprego, desenvolvida com Spring Boot.

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.9+ (ou use o Maven Wrapper incluído)

## 🚀 Como Executar

### Opção 1: Usando Maven Wrapper (Recomendado)

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

### Opção 2: Usando Maven Instalado

```bash
mvn spring-boot:run
```

### Execução com Escolha de Porta

Ao iniciar, uma janela aparecerá solicitando a porta do servidor. Digite a porta desejada (ex: `8080`) ou deixe em branco para usar a porta padrão.

O servidor iniciará e abrirá automaticamente o dashboard administrativo em seu navegador.

## 🔧 Configuração

### Banco de Dados

A aplicação usa H2 Database (em arquivo) com as seguintes configurações padrão:
- **URL**: `jdbc:h2:file:./data/testdb`
- **Usuário**: `sa`
- **Senha**: `password`
- **Console H2**: Habilitado em `/h2-console`

### JWT

- **Expiração**: 1 hora (3600000 ms)
- A chave secreta está definida em `application.properties`

## 📡 Endpoints Principais

### Autenticação
- `POST /login` - Login de usuário/empresa
- `POST /logout` - Logout (requer autenticação)

### Usuários (Candidatos)
- `POST /users` - Criar candidato
- `GET /users/{user_id}` - Buscar candidato
- `PATCH /users/{user_id}` - Atualizar candidato
- `DELETE /users/{user_id}` - Deletar candidato
- `GET /users/{user_id}/jobs` - Listar candidaturas

### Empresas
- `POST /companies` - Criar empresa
- `GET /companies/{company_id}` - Buscar empresa
- `PATCH /companies/{company_id}` - Atualizar empresa
- `DELETE /companies/{company_id}` - Deletar empresa
- `POST /companies/{company_id}/jobs` - Listar vagas da empresa
- `GET /companies/{company_id}/jobs/{job_id}` - Listar candidatos de uma vaga

### Vagas
- `POST /jobs` - Criar vaga (empresa)
- `GET /jobs/{job_id}` - Buscar vaga
- `POST /jobs/search` - Buscar vagas com filtros
- `PATCH /jobs/{job_id}` - Atualizar vaga
- `DELETE /jobs/{job_id}` - Deletar vaga
- `POST /jobs/{job_id}` - Candidatar-se a vaga
- `POST /jobs/{job_id}/feedback` - Enviar feedback ao candidato

### Administração
- `GET /dashboard.html` - Dashboard administrativo
- `GET /admin/dashboard/logs` - Logs de requisições HTTP
- `GET /admin/dashboard/users` - Usuários logados

## 🛠️ Build para Produção

```bash
mvn clean package
```

O arquivo `.jar` será gerado em `target/server-vagas-rest-0.0.1-SNAPSHOT.jar`

Para executar o JAR:
```bash
java -jar target/server-vagas-rest-0.0.1-SNAPSHOT.jar
```

## 📦 Tecnologias

- Spring Boot 3.5.6
- Spring Data JPA
- Spring Security
- JWT (jjwt 0.12.6)
- H2 Database
- Lombok
- Bean Validation

## 🔐 Segurança

- Autenticação via JWT
- Tokens com expiração de 1 hora
- Blacklist de tokens revogados
- CORS configurado para ambientes de desenvolvimento

---

**Nota**: Certifique-se de que a porta escolhida não esteja em uso por outro aplicativo.
