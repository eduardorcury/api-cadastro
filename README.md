[![eduardorcury](https://circleci.com/gh/eduardorcury/api-cadastro.svg?style=svg)](https://circleci.com/gh/eduardorcury/api-cadastro)

# API REST de cadastro de pessoas - Avaliação Elotech

> API de cadastro de pessoas com Java e Spring.

## :wrench: &nbsp;&nbsp; Ferramentas utilizadas

- Java 11
- Spring Boot
- Spring Data JPA
- H2 Database
- Mapstruct
- Hibernate
- JUnit/Mockito

## :bulb: &nbsp;&nbsp; Entidades

![foto](https://github.com/eduardorcury/api-cadastro/blob/master/api-elotech.png)

## :mag_right: &nbsp;&nbsp; Como Usar

A **API** está hospedada em: https://api-cadastro-eduardo.herokuapp.com. As APIs no free tier do Heroku demoram ~10 segundos para repsponder à primeira requisição.

O **Frontend** está hospedado em: https://frontend-cadastro.vercel.app.

É possível ver o console do banco de dados h2 na URL: https://api-cadastro-eduardo.herokuapp.com/h2-console com os dados:
- JDBC URL: jdbc:h2:mem:testdb
- User Name: sa
- Password

Para listagem de usuários, acesse:
```
https://api-cadastro-eduardo.herokuapp.com/api/v1/pessoas
```

Para listagem de usuários paginada, use o seguinte modelo de URL:
```
https://api-cadastro-eduardo.herokuapp.com/api/v1/pessoas/busca?pag=0&qtd=5&ordem=nome&dir=DESC
```

Para testar o cadastro na API, mande uma requisição POST para https://api-cadastro-eduardo.herokuapp.com/api/v1/pessoas com um conteúdo de teste:
```json
{
	"nome" : "Pessoa",
	"cpf" : "897.065.050-40",
	"nascimento" : "2018-02-03",
	"contatos": [
		{
			"nome" : "Contato1",
			"telefone": "112123",
			"email": "contato1@gmail.com"
		},
		{
			"nome" : "Contato2",
			"telefone": "112123",
			"email": "contato2@gmail.com"
		}
	]
}
```
> Somente CPFs válidos são aceitos. Use o [gerador de CPF](https://www.4devs.com.br/gerador_de_cpf).
