# 🏬 Projeto Banco de Dados — Loja de Roupas

## 👥 Equipe
- **Alex Rhikelme Belarmino da Mota**  
- **Diana da Silva**  
- **Gustavo Igor da Silva**

---

## ⚙️ Características Gerais

### 🧩 Linguagem e Implementação
- Linguagem principal: **Java**  
- Banco de dados: **MySQL**
- Todas as operações são executadas via **comandos SQL** integrados à aplicação através da biblioteca **JDBC**.
- A modelagem foi feita utilizando o **BRModelo (BRMW)**.  

O sistema foi estruturado para permitir **criação e destruição completa do banco**, além de conter dados iniciais obrigatórios.

---

## 🛍️ Contexto do Sistema
O sistema representa uma **loja de roupas** com funcionalidades típicas de um e-commerce, incluindo:  
- Cadastro de **clientes** e **clientes especiais**  
- Registro de **vendedores** e **funcionários especiais**  
- **Venda de produtos**, com controle de estoque  
- **Associação de transportadoras** às vendas realizadas  
- **Funções automáticas** (triggers e procedures) que geram relatórios e cálculos em tempo real  

---

## 🧾 Estrutura e Dados Iniciais
O banco foi desenvolvido conforme as seguintes especificações:

- **20 produtos** cadastrados  
- **5 cargos**  
- **100 clientes nativos**  
- Opção de **cadastro de novos produtos e clientes** via sistema  

---

## 📦 Entidades Principais

### 🧍‍♂️ Clientes
- `id`, `nome`, `idade`, `sexo`, `data_nascimento`  

### 🧍‍♂️ Clientes Especiais
- Todos os dados de cliente + `cashback`

### 💼 Vendedores
- `id`, `nome`, `causa_social`, `tipo`, `nota_media`

### 👕 Produtos
- `id`, `nome`, `descricao`, `quantidade_estoque`, `valor`, `observacoes`  
- Cada vendedor pode ofertar vários produtos, mas cada produto pertence a **um único vendedor**.

### 💰 Vendas
- Registro de cada compra com **data e hora**
- Cada venda está associada a **um cliente**, **um vendedor**, **uma transportadora** e **um ou mais produtos**.

### 🚚 Transportadoras
- `id`, `nome`, `cidade`, `valor_transporte`, `endereco_destino`  

---

## 🧮 Funções Implementadas (Functions)

| Função | Descrição |
|--------|------------|
| **calcula_idade(id_cliente)** | Retorna a idade atual de um cliente com base na data de nascimento. |
| **soma_fretes(destino)** | Calcula o valor total arrecadado com fretes para determinado destino. |
| **arrecadado(data, id_vendedor)** | Retorna o total arrecadado por um vendedor em uma data específica. |

---

## ⚡ Gatilhos (Triggers)

| Gatilho | Descrição |
|----------|------------|
| **BonusFuncionario** | Quando um vendedor vende mais de R$ 1000, ele é adicionado à tabela de funcionários especiais e recebe um bônus de **5%** sobre o valor vendido. |
| **CashbackCliente** | Quando um cliente realiza compras acima de R$ 500, ele é adicionado à tabela de clientes especiais e recebe **2% de cashback** sobre o valor gasto.  |
| **RemoveClienteEspecial** | Remove automaticamente um cliente da tabela de clientes especiais caso o valor do cashback chegue a zero. |

---

## 🧑‍💻 Usuários e Permissões

| Usuário | Permissões |
|----------|-------------|
| **Administrador** | Todas as permissões possíveis. |
| **Gerente** | Pode **buscar, apagar e editar** registros. |
| **Funcionário** | Pode **adicionar novas vendas** e **consultar registros de vendas**. |

---

## 📊 Views Criadas
Foram criadas 3 **views** utilizando **JOINs** e **GROUP BY**, com foco em relatórios e estatísticas, como:

- **View de produtos mais vendidos** 
- **View de vendas por cliente**
- **View de vendas por transportadora**

---

## ⚙️ Procedures Implementadas

| Procedure | Descrição |
|------------|------------|
| **reajuste(porc, categoria)** | Aplica um reajuste salarial (em %) a todos os funcionários de uma categoria. |
| **sorteio()** | Sorteia aleatoriamente um cliente para receber um voucher: R$100 (cliente comum) ou R$200 (cliente especial). |
| **venda()** | Reduz automaticamente em 1 a quantidade de um produto vendido no estoque. |
| **estatisticas()** | Exibe estatísticas gerais das vendas, incluindo: <br> - Produto mais e menos vendido <br> - Vendedor associado <br> - Valor total ganho com cada um <br> - Mês com maior e menor volume de vendas. |
 
