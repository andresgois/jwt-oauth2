# Getting Started

## URL's do sistema
- http://localhost:8080/users/teste/ola
- http://localhost:8080/h2-console

## Security
- Ao adicionar a dependência do spring-security
- todas as rotas necessitam de uma autênticação
- Quando o Spring é inicializado, ele gera um password que você coloca para consulra:
- por exemplo: via postman
  - Na aba Authorization
    - Basic Auth
      - user: user
      - password: Using generated security password: 52a78a62-4f84-436e-8175-6db14d3cbfd0
  - ele gera isso no console da aplicação
- Você pode adicionar um usuário ao application.yml
```
Spring:
  security:
    user:
      name: andre
      password: 123
```
## Gerando chave publica e privada
> Chave privada
-  openssl genpkey -algorithm RSA -out private_key.pem
> Chave Pública
- openssl rsa -pubout -in private_key.pem -out public_key.pem