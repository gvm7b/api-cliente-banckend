#  API Cliente Backend

API REST desenvolvida com **Spring Boot** para gerenciamento de clientes e envio de e-mails.  
O projeto simula um sistema básico com cadastro de usuários e funcionalidades de comunicação via e-mail (texto e HTML).

---

##  Tecnologias utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Java Mail Sender
- Maven

---

##  Funcionalidades

###  Cliente
- Criar cliente
- Listar clientes
- Atualizar cliente
- Deletar cliente (Soft Delete)

###  Email
- Envio de email simples
- Envio de email com HTML

---

##  Conceitos aplicados

- API REST
- CRUD completo
- Integração com serviço de email (SMTP)
- Soft Delete com Hibernate
- Boas práticas com JPA
- Arquitetura em camadas

---

##  Soft Delete

O projeto utiliza exclusão lógica (soft delete), ou seja, os dados não são removidos do banco, apenas marcados como deletados:

```java
@SQLDelete(sql = "UPDATE cliente SET delete_at = now() WHERE id=?")
@Where(clause = "delete_at is null")
