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

1. **Clone o repositório**: Faça uma cópia do projeto para o seu computador.
   ```bash
   git clone git@github.com:J-edu-lima/EstoqueAPI.git
   ```

2. **Entre na pasta do projeto**:
   ```bash
   cd EstoqueAPI
   ```

3. **Configure o banco de dados**:
   - Crie um banco de dados chamado `estoqueAPI` no MsSQL.
   - Atualize as configurações de conexão no arquivo `application.properties`.

4. **Execute o projeto**: Comece a usar o programa e faça as requisiçoes pelo postman ou em outra plataforma.

5. **Endpoints**: A collection do Postman está salvo em "postmanRequisições", sendo necessario apenas a importação para o seu postman.

## Futuras Atualizações

**Este projeto é um MVP e há planos para futuras atualizações, incluindo**:
   - Implementação de autenticação e autorização.
   - Interface gráfica para facilitar a interação do usuário.
   - Relatórios detalhados de vendas e estoque.

## Contribuições
 Se você quiser ajudar a melhorar o projeto, fique à vontade para sugerir ideias ou fazer alterações.
