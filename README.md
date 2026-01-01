# 📝 Todo List – Spring Boot MVC

Aplicação web de gerenciamento de tarefas desenvolvida com **Spring Boot MVC**, **Thymeleaf** e **Spring Security**, permitindo que usuários criem anotações (notes) e gerenciem tarefas associadas a cada anotação, com autenticação, autorização e controle de acesso por perfil.

---

## 🚀 Funcionalidades

### 👤 Usuários
- Cadastro de usuários
- Login com autenticação via Spring Security
- Controle de acesso baseado em roles (`USER` e `ADMIN`)
- Usuário administrador criado automaticamente na inicialização

### 🗂 Notas (Notes)
- Criar, editar e excluir notas
- Cada nota pertence exclusivamente ao usuário autenticado
- Proteção contra acesso indevido a notas de outros usuários

### ✅ Tarefas (Tasks)
- Criar tarefas vinculadas a uma nota
- Editar e excluir tarefas
- Definir prazo (deadline)
- Validação de regras de negócio:
  - Título obrigatório
  - Prazo não pode ser no passado

### 🔐 Segurança
- Autenticação via formulário personalizado
- Senhas criptografadas com **BCrypt**
- Autorização por perfil:
  - Apenas `ADMIN` pode listar usuários
- Proteção contra CSRF (com exceções controladas)
- Sessão invalidada no logout

---

## 🛠 Tecnologias Utilizadas

- Java 17 (compatível com versões superiores)
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Thymeleaf
- H2 Database
- Maven
---
## 🧱 Arquitetura

O projeto segue uma arquitetura em camadas:

- **Controller**: Responsável pelas rotas e views
- **Service**: Regras de negócio e validações
- **Repository**: Persistência de dados (JPA)
- **Model**: Entidades do domínio
---

## 🗃 Modelo de Dados (Resumo)

- **Usuario**
  - Possui várias Notes
  - Possui Role (`ADMIN` ou `USER`)

- **Note**
  - Pertence a um usuário
  - Possui várias Tasks

- **Task**
  - Pertence a uma Note
  - Pode ter prazo (deadline)

---

## ▶️ Como Executar o Projeto

### Pré-requisitos
- Java 17+
- Maven

### Passos

```bash
git clone https://github.com/RuanSpinelli/todo-list-springbootapi.git
cd todo-list-springbootapi
mvn spring-boot:run
```

Acesse no navegador:
```bash
http://localhost:8080
```
---
## 🔑 Usuário Administrador Padrão

Ao iniciar a aplicação, um usuário administrador é criado automaticamente:

- **Usuário:** admin  
- **Senha:** admin123  
- **Perfil:** ADMIN

Esse usuário pode acessar funcionalidades restritas, como a listagem de usuários.
---
## 📝 Observações

- Banco de dados H2 em memória
- Console H2 habilitado para ambiente de desenvolvimento
- Projeto com foco educacional e boas práticas em Spring Boot MVC

Autor: [Ruan Spinelli](https://github.com/RuanSpinelli)
