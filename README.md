# Backend - Desafio Técnico | Arquiteto de Soluções

![Diagrama de Arquitetura Geral](https://raw.githubusercontent.com/phillippimenta/assets-desafio-arquiteto-solucoes/main/general-architecture-diagram.svg)

Este repositório contém a implementação do backend para o **Desafio Técnico de Arquiteto de Soluções (Desenvolvedor Sênior)**, com foco em escalabilidade, segurança, mensageria e boas práticas de arquitetura.

## 📌 Descrição do Desafio

Um banco tradicional está se reposicionando para atender o público geral, disputando mercado com fintechs e bancos digitais. Para isso, foi criada uma campanha de pré-lançamento com um QR Code exibido na TV, direcionando os usuários para um site de **pré-cadastro**.

Este backend é responsável por:

- Receber e validar dados de pré-cadastro de leads (nome, e-mail e telefone)
- Persistir os dados no banco de dados relacional
- Publicar os dados em uma fila RabbitMQ para processamento posterior
- Expor documentação via Swagger/OpenAPI
- Garantir observabilidade, segurança e boas práticas de engenharia

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- PostgreSQL
- RabbitMQ
- Spring Data JPA
- Spring Validation
- Springdoc OpenAPI
- Docker & Docker Compose

## 🧱 Estrutura do Projeto

```
src/
├── application/         # Casos de uso e lógica de orquestração
├── config/              # Configurações de beans, OpenAPI, filas, etc.
├── domain/              # Entidades e interfaces de repositórios
├── infrastructure/      # Implementações técnicas (JPA, mensageria, etc.)
├── presentation/        # Controllers e DTOs
└── LeadmagnetApplication.java
```

## ⚙️ Como Executar Localmente

1. **Clone o repositório**
```bash
git clone https://github.com/phillippimenta/backend-desafio-arquiteto-solucoes.git
cd backend-desafio-arquiteto-solucoes
```

2. **Configure variáveis de ambiente**

Crie um arquivo `.env` em `src/docker/.env` com as seguintes variáveis:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/leadmagnet
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
RABBITMQ_EXCHANGE=leadmagnet.exchange
RABBITMQ_ROUTING_KEY=lead.created
```

3. **Suba os serviços com Docker Compose**
```bash
docker-compose up --build
```

4. **Acesse a documentação da API**
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🧪 Testes

Para rodar os testes automatizados:

```bash
./mvnw test
```

## 📬 Fila RabbitMQ

Ao cadastrar um novo lead, a aplicação:

- Publica uma mensagem no exchange `leadmagnet.exchange`
- Utiliza a routing key `lead.created`
- A mensagem contém os dados do lead em formato JSON

## 📄 Licença

Este projeto é apenas para fins educacionais e técnicos, desenvolvido como parte de um desafio de arquitetura de soluções.

## 👤 Autor

**Phillip Pimenta**  
Desenvolvedor Java | Arquiteto de Software  
[LinkedIn](https://www.linkedin.com/in/phillippimenta)
