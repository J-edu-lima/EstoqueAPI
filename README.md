# MVP de Estoque

## Descrição

Este projeto é uma API de gerenciamento de estoque, projetada para facilitar o controle de produtos para pequenos lojistas. A API permite registrar, atualizar, buscar e deletar produtos, além de gerenciar entradas e saídas de estoque. Um dos diferenciais desta API é o sistema de cálculo automático, que permite ao usuário inserir uma porcentagem sobre o custo do produto e receber o valor de venda, bem como o valor das saídas conforme a quantidade vendida.

## Funcionalidades

- **Registrar Produtos**: Adiciona novos produtos ao estoque utilizando o seu código de barras, nome, valor de compra, quantidade total e porcentagem sobre venda como informações básicas.
- **Atualizar Produtos**: Modifique as informações dos produtos já cadastrados, podendo modificar todas de uma vez ou somente uma (com exceção do preço de venda).
- **Buscar Produtos**: Consulte detalhes dos produtos no estoque, incluindo busca pelas entradas e saidas.
- **Deletar Produtos**: Remova produtos do estoque quando necessário.
- **Gerenciar Entradas e Saídas**: Registre a entrada e a saída de produtos do estoque.
- **Cálculo Automático**: Insira uma porcentagem sobre venda para obter automaticamente o valor de venda do produto.

## Tecnologias Utilizadas

- [Spring Boot](https://spring.io/projects/spring-boot) - Framework para construção de APIs Java.
- [Microsoft SQL Server (MSSQL)](https://www.microsoft.com/en-us/sql-server/sql-server-downloads) - Sistema de gerenciamento de banco de dados, que permite integração com o Power BI para maior controle de estoque.

## Como Executar

1. Clone o repositório:
   ```bash
   git clone git@github.com:J-edu-lima/EstoqueAPI.git
Navegue até o diretório do projeto:

bash
Copiar código
cd EstoqueAPI
Configure seu banco de dados e atualize as credenciais no arquivo application.properties.

Execute o projeto:

bash
Copiar código
mvn spring-boot:run
Acesse a API utilizando postman em http://localhost:8080.

Endpoints (Collection do Postman salvo em "PostmanRequisições")
POST v1/produto: Adiciona um novo produto.
PUT v1/produto/{id}: Atualiza as informações de um produto existente.
GET v1/produto: Lista todos os produtos.
DELETE v1/produto/{id}: Remove um produto do estoque.
POST v1/entrada/{id}: Registra a entrada de uma quantidade de produtos.
POST v1/saida/{id}: Registra a saída de uma quantidade de produtos.

Futuras Atualizações
Este projeto é um MVP e há planos para futuras atualizações, incluindo:

Implementação de autenticação e autorização.
Interface gráfica para facilitar a interação do usuário.
Relatórios detalhados de vendas e estoque.
Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.
