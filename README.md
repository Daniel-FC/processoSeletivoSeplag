# Music API Project

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)

API REST para gerenciamento de artistas e álbuns, desenvolvida como um projeto prático para avaliação de habilidades em desenvolvimento Back-End Java Sênior.

## ✨ Features

- **CRUD Completo:** Gerenciamento de Artistas e Álbuns com relacionamento N:N.
- **Autenticação e Segurança:** Acesso protegido por JWT com expiração e renovação.
- **Upload de Arquivos:** Envio de imagens de capa para o MinIO (S3-compatible).
- **URLs Pré-Assinadas:** Geração de links temporários (30 min) para acesso seguro às imagens.
- **Paginação e Ordenação:** Suporte completo para paginação e ordenação em endpoints de listagem.
- **Sincronização Agendada:** Job que sincroniza dados de uma API externa de forma periódica.
- **Notificações em Tempo Real:** WebSocket (STOMP) que notifica o front-end sobre novos álbuns.
- **Rate Limiting:** Limite de 10 requisições por minuto por usuário.
- **Health Checks:** Endpoints para monitoramento da saúde da aplicação (`/actuator/health`).
- **Documentação Interativa:** API documentada com Swagger/OpenAPI.

## 🛠️ Tech Stack

- **Linguagem:** Java 17
- **Framework:** Spring Boot 3.2.2
- **Módulos Spring:** Web, Data JPA, Security, WebSocket, Actuator
- **Banco de Dados:** PostgreSQL
- **Migrations:** Flyway
- **Armazenamento de Arquivos:** MinIO (S3 Compatible)
- **Autenticação:** JWT (JSON Web Tokens)
- **Mapeamento de Objetos:** ModelMapper
- **Containerização:** Docker & Docker Compose

## 🚀 Getting Started

### Pré-requisitos
- Docker
- Docker Compose

### Executando o Projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/music-api.git
   cd music-api
   ```

2. **Inicie os containers:**
   Navegue até a pasta `docker` e execute o comando:
   ```bash
   docker-compose up --build
   ```

3. **Aguarde os serviços iniciarem.** Os seguintes serviços estarão disponíveis:
   - **Music API:** `http://localhost:8080`
   - **MinIO Console:** `http://localhost:9001` (Login: `minioadmin` / `minioadmin`)
   - **PostgreSQL:** `localhost:5432`

## 📚 API Usage & Examples

A API é protegida por JWT. Para acessar os endpoints, você primeiro precisa obter um token.

### 1. Obter o Token de Autenticação

Faça uma requisição `POST` para o endpoint de login.

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
{
  "username": "admin",
  "password": "password"
}
```
**Resposta:** Você receberá o token JWT no corpo da resposta. Copie este token.

### 2. Fazer uma Requisição Autenticada

Para acessar um endpoint protegido, adicione o token ao cabeçalho `Authorization`.

**Exemplo: Listar todos os artistas**

```bash
TOKEN="eyJhbGciOiJIUzI1NiJ9..."

curl -X GET http://localhost:8080/api/v1/artists
-H "Authorization: Bearer $TOKEN"
```
*(No Windows ou em clientes de API como o Postman, substitua `$TOKEN` pelo token copiado).*

## 📄 API Documentation (Swagger)

A documentação interativa da API (Swagger UI) está disponível e é a melhor forma de explorar todos os endpoints.

- **URL:** `http://localhost:8080/swagger-ui.html`

Para usar os endpoints protegidos no Swagger, clique no botão **"Authorize"** no canto superior direito e cole o seu token JWT no campo `Value`.

## ⚙️ Environment Variables

As principais variáveis de configuração podem ser encontradas no arquivo `docker-compose.yml`.

| Variável                    | Descrição                                      | Valor Padrão                     |
| --------------------------- | ---------------------------------------------- | -------------------------------- |
| `SPRING_DATASOURCE_URL`     | URL de conexão com o PostgreSQL.               | `jdbc:postgresql://postgres:5432/musicdb` |
| `MINIO_URL`                 | URL do serviço MinIO.                          | `http://minio:9000`              |
| `MINIO_ACCESS_KEY`          | Chave de acesso do MinIO.                      | `minioadmin`                     |
| `MINIO_SECRET_KEY`          | Chave secreta do MinIO.                        | `minioadmin`                     |
| `JWT_SECRET`                | Segredo para assinar os tokens JWT.            | (definido no `application.yml`)  |
| `JWT_EXPIRATION`            | Tempo de expiração do token em milissegundos.  | `300000` (5 minutos)             |

---
Este projeto foi desenvolvido como parte de um processo de avaliação, demonstrando conhecimento em arquitetura de software, boas práticas e tecnologias modernas do ecossistema Java.
