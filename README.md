# Projeto

Este repositório contém a configuração de um sistema baseado em **Docker
Compose**, com dois serviços principais:

- **backend**: Responsável pela lógica de negócio e comunicação com o banco de
  dados.
- **bff (Backend for Frontend)**: Atua como intermediário entre o frontend e o
  backend, facilitando a exposição das APIs.

Cada serviço tem um **README.md** dentro de sua respectiva pasta (**backend/** e
**bff/**) com mais detalhes sobre sua configuração e execução.

## Tecnologias Utilizadas

- **Docker** e **Docker Compose**
- **Node.js / Express** (para o BFF)
- **Java / Spring Boot** (para o Backend)
- **PostgreSQL** (caso haja persistência de dados)

## Como Rodar o Projeto

1. **Clone o repositório**
   ```sh
   git clone https://github.com/marcosoliveeira1/api-controle-escolar.git
   cd api-controle-escolar
   ```

2. **Inicie os containers**
   ```sh
   docker compose up --build
   ```
   - Para rodar os containers em background (modo "detached"), use:
     ```sh
     docker compose up -d --build
     ```

3. **Acesse os serviços**
   - Backend: [http://localhost:8080](http://localhost:8080)
   - BFF: [http://localhost:3000](http://localhost:3000)

4. **Parar os containers**
   ```sh
   docker compose down
   ```

## Informações Adicionais

- Para mais detalhes sobre cada módulo, consulte os arquivos **README.md**
  dentro das pastas `backend/` e `bff/`.
- Certifique-se de que possui **Docker** e **Docker Compose** instalados antes
  de rodar o projeto.
