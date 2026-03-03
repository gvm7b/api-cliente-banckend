#  API Cliente Backend

API REST desenvolvida com **Spring Boot** para gerenciamento de clientes, endereços e envio de e-mails.  

📚 Este projeto foi desenvolvido em **2023 durante um curso no SENAC**, com o objetivo de aplicar conceitos fundamentais de desenvolvimento backend utilizando Java e Spring Boot.

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

###  Endereço
- Cadastro de endereço vinculado ao cliente
- Associação entre cliente e endereço

###  Email
- Envio de email simples
- Envio de email com HTML

---

##  Conceitos aplicados

- API REST
- CRUD completo
- Relacionamento entre entidades (Cliente ↔ Endereço)
- Integração com serviço de email (SMTP)
- Soft Delete com Hibernate
- Mapeamento com JPA
- Estrutura em camadas

---

##  Soft Delete

O projeto utiliza exclusão lógica (soft delete), garantindo que os dados não sejam removidos permanentemente do banco:

```java
@SQLDelete(sql = "UPDATE cliente SET delete_at = now() WHERE id=?")
@Where(clause = "delete_at is null")
