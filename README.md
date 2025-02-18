# EstoqueAPI

## DOCUMENTAÇÃO

### Resumo

**EstoqueAPI** é uma API de gerenciamento de estoque, projetada para facilitar o controle de produtos para pequenos lojistas. O sistema permite realizar operações essenciais, como **registrar, atualizar, buscar e deletar produtos**, além de **gerenciar entradas e saídas de produtos**. A API também oferece um método de cálculo que **precifica automaticamente os produtos** ao serem cadastrados no sistema e uma interface visual feita utilizando JFrame.

### Funcionalidades

- **Cadastro de Produtos**: Adiciona novos produtos ao estoque.
- **Entrada de Produtos**: Registra a entrada de novos produtos no estoque.
- **Saída de Produtos**: Gerencia a saída de produtos do estoque.
- **Precificação Automática**: O sistema aplica regras de precificação com base em porcentagens sobre o valor do produto.

### Tecnologias Utilizadas

- **Spring JPA**: Framework para persistência de dados utilizando JPA.
- **MySQL**: Banco de dados relacional para armazenar dados do estoque.
- **JFrame**: Interface gráfica para interação com o usuário.
- **Postman**: Ferramenta para testar as rotas da API e interações com o sistema.

### Pré-Requisitos

- **Java 17+**: Certifique-se de ter a versão 17 ou superior do Java.
- **Banco de Dados Relacional**: MySQL (ou outro banco de dados relacional) para persistir os dados.
- **IDE**: IntelliJ IDEA, Eclipse ou qualquer outra IDE de sua escolha.
  
---

### Estrutura de Diretórios

A estrutura do projeto segue a arquitetura **MVC (Model-View-Controller)** e é organizada conforme abaixo:

```
/src
 └── /main
     └── /java
         └── /jedu_lima
             └── /EstoqueAPI
                 ├── /client                   # Responsável por interações com a API externa
                 │   ├── ClientAPI.java         # Classe de conexão da interface com a API
                 ├── /controller               # Lida com as requisições HTTP e lógica do controlador
                 │   ├── ProdutoCadastroController.java
                 │   ├── ProdutoEntradaController.java
                 │   ├── ProdutoSaidaController.java
                 ├── /entity                   # Contém as entidades do modelo de dados
                 │   ├── ProdutoCadastro.java
                 │   ├── ProdutoEntrada.java
                 │   ├── ProdutoSaida.java
                 ├── /model                    # Contém os DTOs e o mapeamento entre eles e as entidades
                 │   ├── /mapper                # Contém as classes responsáveis por mapear DTOs para entidades
                 │   │   ├── ProdutoCadastroMapper.java
                 │   │   ├── ProdutoEntradaMapper.java
                 │   │   ├── ProdutoSaidaMapper.java
                 │   ├── CriarProdutoCadastroEntradaDTO.java
                 │   ├── CriarProdutoEntradaDTO.java
                 │   ├── CriarProdutoSaidaDTO.java
                 ├── /repository               # Contém as interfaces de repositórios (extensão do JPARepository)
                 │   ├── ProdutoCadastroRepository.java
                 │   ├── ProdutoEntradaRepository.java
                 │   ├── ProdutoSaidaRepository.java
                 ├── /service                  # Contém as interfaces e implementações das regras de negócio
                 │   ├── /impl                 # Contém as implementações das interfaces de serviço
                 │   │   ├── CadastroServiceImpl.java
                 │   │   ├── CalculoServiceImpl.java
                 │   │   ├── EntradaServiceImpl.java
                 │   │   ├── UiEntradaServiceImpl.java
                 │   │   ├── UiProdutoServiceImpl.java
                 │   │   ├── UiSaidaServiceImpl.java
                 │   │   ├── VerificacaoServiceImpl.java
                 │   ├── CadastroService.java
                 │   ├── CalculoService.java
                 │   ├── EntradaService.java
                 │   ├── SaidaService.java
                 ├── /view                     # Contém as classes relacionadas à interface do usuário
                 │   ├── InterfaceEntradas.java
                 │   ├── InterfacePrincipal.java  # Tela principal, que leva às outras telas
                 │   ├── InterfaceProdutos.java
                 │   ├── InterfaceSaidas.java
```

---

### Dependências

As principais dependências utilizadas no projeto estão listadas no arquivo `pom.xml` e incluem:

- **Spring Boot Starter Data JPA**
- **Spring Boot Starter Web**
- **DevTools**
- **MySQL Connector**
- **Jackson e FasterXML**

Essas dependências são essenciais para o funcionamento do sistema e interações com o banco de dados.

---

### Instalação e Execução

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/J-edu-lima/EstoqueAPI.git
   ```

2. **Abra o projeto em sua IDE** (recomendamos IntelliJ IDEA ou Eclipse).

3. **Configuração do Banco de Dados**:
   - Acesse o arquivo `application.properties` e configure as credenciais do banco de dados MySQL ou de outro banco de sua escolha.

4. **Rodando o Projeto**:
   - Execute a classe **`InterfacePrincipal.java`** para iniciar o sistema.
   - A API estará disponível em `http://localhost:8080` após a execução.

5. **Testando a API**:
   - Importe o arquivo **`PostmanRequisicoes.json`** para o Postman e utilize as requisições já configuradas.

---

### Classes e Métodos Importantes

- **ClientAPI.java**: Responsável pela comunicação com APIs externas, com funções para realizar requisições HTTP (RESTful, por exemplo).
  
- **Controller**: Os controladores lidam com as requisições HTTP, interagem com os serviços e retornam os resultados.
    - **ProdutoCadastroController.java**
    - **ProdutoEntradaController.java**
    - **ProdutoSaidaController.java**
  
- **Entity**: Representa as entidades do modelo de dados, mapeando para o banco de dados.
    - **ProdutoCadastro.java**
    - **ProdutoEntrada.java**
    - **ProdutoSaida.java**
  
- **Model**: Contém os DTOs (Data Transfer Objects) utilizados para transferir dados entre as camadas.
    - **CriarProdutoCadastroEntradaDTO.java**
    - **CriarProdutoEntradaDTO.java**
    - **CriarProdutoSaidaDTO.java**
  
- **Repository**: Interfaces que estendem `JpaRepository` para interagir com o banco de dados.
    - **ProdutoCadastroRepository.java**
    - **ProdutoEntradaRepository.java**
    - **ProdutoSaidaRepository.java**
  
- **Service**: Contém a lógica de negócios e regras de processamento de dados.
    - **ProdutoCadastroService.java**
    - **ProdutoEntradaService.java**
    - **ProdutoSaidaService.java**
    - **CalculoService.java**

---

### Método `CalculoServiceImpl`

A classe **`CalculoServiceImpl`** contém a lógica de cálculo e precificação de produtos. Os métodos importantes são:

- **`calcularValorVenda(BigDecimal valorCompra, double porcentagem)`**: Calcula o valor final de venda de um produto com base no valor de compra e na porcentagem de lucro.

- **`calcularValorTotalVenda(BigDecimal valorVenda, int quantidade)`**: Calcula o valor total de vendas com base no valor de venda unitário e a quantidade de produtos.

- **Soma e Subtração de Estoque**: Métodos responsáveis por adicionar ou remover quantidades do estoque.

---

### Documentação da API

A documentação das rotas da API já está configurada e pode ser importada diretamente no **Postman**. O arquivo **`PostmanRequisicoes.json`** contém todas as requisições configuradas para testar os endpoints do sistema.

---

### Contribuindo

Contribuições são bem-vindas! Siga o fluxo de **fork**, **commit** e **pull request** para adicionar melhorias ou correções ao projeto.

---

### Imagens

![Tela Principal](https://github.com/J-edu-lima/EstoqueAPI/blob/main/images/InterfacePrincipal.png?raw=true)
![Produtos Cadastrados](https://github.com/J-edu-lima/EstoqueAPI/blob/main/images/ProdutosCadastrados.png?raw=true)
![Deletar Entradas](https://github.com/J-edu-lima/EstoqueAPI/blob/main/images/DeletarEntrada.png?raw=true)
![Saida Cadastrada](https://github.com/J-edu-lima/EstoqueAPI/blob/main/images/SaidaCadastro.png?raw=true)

---
