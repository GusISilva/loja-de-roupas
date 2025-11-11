package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Scanner;

import database.CreateBD;

public class ApplicationRunner {

    private final Scanner scanner;
    private final Connection connection;

    public ApplicationRunner(Connection connection, Scanner scan) {
        this.scanner = scan;
        this.connection = connection;
    }

    public void start() throws SQLException {
        boolean executando = true;
        while (executando) {
            System.out.println("\n=== LOGIN DO SISTEMA ===");
            System.out.println("1 - Administrador");
            System.out.println("2 - Gerente");
            System.out.println("3 - Funcionário");
            System.out.println("0 - Encerrar Sistema");
            System.out.print("Escolha o tipo de usuário: ");
            String tipo = scanner.nextLine().trim();

            switch (tipo) {
                case "1" -> {
                    System.out.print("Digite a senha do Administrador: ");
                    String senha = scanner.nextLine();
                    if (senha.equals("admin123")) {
                        menuAdministrador();
                    } else {
                        System.out.println("Senha incorreta! Acesso negado.");
                    }
                }
                case "2" -> {
                    System.out.print("Digite a senha do Gerente: ");
                    String senha = scanner.nextLine();
                    if (senha.equals("gerente123")) {
                        menuGerente();
                    } else {
                        System.out.println("Senha incorreta! Acesso negado.");
                    }
                }
                case "3" -> {
                    System.out.print("Digite a senha do Funcionário: ");
                    String senha = scanner.nextLine();
                    if (senha.equals("func123")) {
                        menuFuncionario();
                    } else {
                        System.out.println("Senha incorreta! Acesso negado.");
                    }
                }
                case "0" -> {
                    System.out.println("Encerrando sistema...");
                    executando = false;
                }
                default -> System.out.println("Tipo inválido. Tente novamente.");
            }
        }
    }


    private void menuAdministrador() throws SQLException {
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== MENU ADMINISTRADOR ===");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Cadastrar Produto");
            System.out.println("3 - Cadastrar Venda");
            System.out.println("4 - Consultar Views");
            System.out.println("5 - Executar Procedures");
            System.out.println("6 - Deletar Banco de Dados");
            System.out.println("0 - Voltar ao Login");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> cadastrarCliente();
                case "2" -> cadastrarProduto();
                case "3" -> cadastrarVenda();
                case "4" -> consultarViews();
                case "5" -> executarProcedures();
                case "6" -> {
                    System.out.println("Tem certeza que deseja deletar o banco de dados? (S/N)");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("S")) {
                        CreateBD.delete();
                        System.out.println("Banco de dados deletado!");
                        rodando = false;
                    } else {
                        System.out.println("Operação cancelada.");
                    }
                }
                case "0" -> {
                    System.out.println("Voltando ao login...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void menuGerente() throws SQLException {
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== MENU GERENTE ===");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Consultar Views");
            System.out.println("3 - Executar Procedures");
            System.out.println("0 - Voltar ao Login");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> cadastrarProduto();
                case "2" -> consultarViews();
                case "3" -> executarProcedures();
                case "0" -> {
                    System.out.println("Voltando ao login...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void menuFuncionario() throws SQLException {
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== MENU FUNCIONÁRIO ===");
            System.out.println("1 - Cadastrar Venda");
            System.out.println("2 - Consultar Views");
            System.out.println("0 - Voltar ao Login");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> cadastrarVenda();
                case "2" -> consultarViews();
                case "0" -> {
                    System.out.println("Voltando ao login...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarCliente() throws SQLException {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        System.out.print("Sexo (M/F/O): ");
        String sexo = scanner.nextLine().toUpperCase();
        System.out.print("Data de Nascimento (YYYY-MM-DD): ");
        String dataNasc = scanner.nextLine();

        try (Statement stmt = connection.createStatement()) {
            String sql = String.format(
                    "INSERT INTO clientes (nome, idade, sexo, dataNasc) VALUES ('%s', %d, '%s', '%s')",
                    nome, idade, sexo, dataNasc);
            stmt.executeUpdate(sql);
            System.out.println("Cliente cadastrado com sucesso!");
        }
    }

    private void cadastrarProduto() throws SQLException {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Quantidade: ");
        int quantidade = Integer.parseInt(scanner.nextLine());
        System.out.print("Valor: ");
        double valor = Double.parseDouble(scanner.nextLine());
        System.out.print("Observações: ");
        String obs = scanner.nextLine();

        try (Statement stmt = connection.createStatement()) {
            String sql = String.format(
                    "INSERT INTO produtos (nome, quantidade, valor, observacoes) VALUES ('%s', %d, %s, '%s')",
                    nome, quantidade, String.format(Locale.US, "%.2f", valor), obs);
            stmt.executeUpdate(sql);
            System.out.println("Produto cadastrado com sucesso!");
        }
    }

    private void cadastrarVenda() throws SQLException {
        System.out.print("ID do Cliente: ");
        long idCliente = Long.parseLong(scanner.nextLine());
        System.out.print("ID do Vendedor: ");
        long idVendedor = Long.parseLong(scanner.nextLine());
        System.out.print("ID da Transportadora: ");
        long idTrans = Long.parseLong(scanner.nextLine());
        System.out.print("Endereço de destino: ");
        String endereco = scanner.nextLine();
        System.out.print("Valor cobrado: ");
        double valor = Double.parseDouble(scanner.nextLine());

        try (Statement stmt = connection.createStatement()) {

            double cashback = 0.0;
            var rs = stmt.executeQuery("SELECT cashback FROM cliente_especial WHERE id_cliente = " + idCliente);
            if (rs.next()) {
                cashback = rs.getDouble("cashback");
            }

            double valorFinal = valor - cashback;
            if (valorFinal < 0) valorFinal = 0;

            String sqlVenda = String.format(Locale.US,
                    "INSERT INTO venda (endereco_destino, valor_cobrado, data_hora, id_vendedor, id_cliente, id_trans) " +
                            "VALUES ('%s', %.2f, NOW(), %d, %d, %d)",
                    endereco, valorFinal, idVendedor, idCliente, idTrans);
            stmt.executeUpdate(sqlVenda);

            if (cashback > 0) {
                stmt.executeUpdate("DELETE FROM cliente_especial WHERE id_cliente = " + idCliente);
                System.out.println("Cashback de R$ " + cashback + " aplicado e cliente removido da lista de especiais.");
            }

            System.out.println("Venda cadastrada com sucesso! Valor final: R$ " + valorFinal);
        }
    }

    private void consultarViews() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            System.out.println("=== Produtos mais vendidos ===");
            var rs = stmt.executeQuery("SELECT * FROM view_produtos_mais_vendidos");
            while (rs.next()) {
                System.out.println("Produto: " + rs.getString("produto") +
                        ", Quantidade vendida: " + rs.getInt("quantidade_vendida") +
                        ", Vendedor: " + rs.getString("vendedor"));
            }

            System.out.println("\n=== Total de vendas por cliente ===");
            rs = stmt.executeQuery("SELECT * FROM view_vendas_por_cliente");
            while (rs.next()) {
                System.out.println("Cliente: " + rs.getString("cliente") +
                        ", Total gasto: " + rs.getBigDecimal("total_gasto"));
            }

            System.out.println("\n=== Total de vendas por transportadora ===");
            rs = stmt.executeQuery("SELECT * FROM view_vendas_por_transportadora");
            while (rs.next()) {
                System.out.println("Transportadora: " + rs.getString("transportadora") +
                        ", Total vendas: " + rs.getInt("total_vendas") +
                        ", Valor total: " + rs.getBigDecimal("valor_total"));
            }
        }
    }

    private void executarProcedures() throws SQLException {
        Scanner scan = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Menu de Procedures ===");
            System.out.println("1 - Reajuste salarial por cargo");
            System.out.println("2 - Sorteio de cliente");
            System.out.println("3 - Venda (reduz estoque)");
            System.out.println("4 - Estatísticas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scan.nextLine());

            try (Statement stmt = connection.createStatement()) {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("Informe o cargo para reajustar (ex: vendedor, gerente, CEO): ");
                        String cargo = scan.nextLine();

                        System.out.print("Informe o percentual de aumento (ex: 10 para 10%): ");
                        double percentual = Double.parseDouble(scan.nextLine());

                        stmt.execute(String.format(Locale.US, "CALL Reajuste(%.2f, '%s')", percentual, cargo));
                        System.out.println("Reajuste de " + percentual + "% aplicado com sucesso ao cargo: " + cargo);
                    }
                    case 2 -> {
                        var rsSorteio = stmt.executeQuery("CALL Sorteio()");
                        if (rsSorteio.next()) {
                            long id = rsSorteio.getLong("id");
                            String tipo = rsSorteio.getString("tipo");
                            double voucher = rsSorteio.getDouble("voucher");
                            System.out.println("Cliente sorteado: ID " + id + ", Tipo: " + tipo +
                                    ", Voucher convertido em cashback: R$ " + voucher);
                        }
                    }
                    case 3 -> {
                        System.out.print("Informe o ID do produto: ");
                        long idProduto = Long.parseLong(scan.nextLine());
                        System.out.print("Informe a quantidade: ");
                        int qtd = Integer.parseInt(scan.nextLine());
                        stmt.execute(String.format("CALL Venda(%d, %d)", idProduto, qtd));
                        System.out.println("Venda executada e estoque atualizado!");
                    }
                    case 4 -> {
                        boolean hasResults = stmt.execute("CALL Estatisticas()");
                        int resultSetIndex = 1;
                        while (hasResults) {
                            try (var rs = stmt.getResultSet()) {
                                System.out.println("=== Resultado " + resultSetIndex + " ===");
                                while (rs.next()) {
                                    System.out.println(rs.getString("Tipo") + " -> Produto: " + rs.getString("Produto") +
                                            ", Vendedor: " + rs.getString("Vendedor") +
                                            ", Qtde vendida: " + rs.getInt("Qtde_vendida") +
                                            ", Valor total: R$" + rs.getDouble("Valor_total") +
                                            ", Mês maior vendas: " + rs.getInt("Mes_maior_vendas") +
                                            ", Mês menor vendas: " + rs.getInt("Mes_menor_vendas"));
                                }
                            }
                            hasResults = stmt.getMoreResults();
                            resultSetIndex++;
                        }
                    }
                    case 0 -> continuar = false;
                    default -> System.out.println("Opção inválida!");
                }
            }
        }
    }
}
