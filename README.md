# senior-challenge-be

Subindo o servidor localmente:

Montar imagem do banco:
>docker-compose up

Subir aplicação:
>mvn spring-boot:run

Cadastrar quartos para utilizar no sistema (ainda não possui tela de cadastro de quartos):
Método: POST
Ncessário: autenticação: NÂO
endpoint: http://localhost:8080/api/quartos
json exemplo:   { "nome": "xx", "preco": 120.00, "imagem": "555", "locado": false }


#API segue padrões REST

url base: 'http://localhost:8080/api'
CRUDS da aplicação
Pessoa: '/pessoas'
Quarto: '/quartos'
Movimentação: '/movimentacao'

Chamadas da API sempre na raiz (ex. 'http://localhost:8080/api/pessoas' )

