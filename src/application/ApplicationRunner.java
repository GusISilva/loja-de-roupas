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
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== MENU DO SISTEMA E-COMMERCE ===");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Cadastrar Produto");
            System.out.println("3 - Cadastrar Venda");
            System.out.println("4 - Consultar Views");
            System.out.println("5 - Executar Procedures");
            System.out.println("6 - Deletar Banco de Dados");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    cadastrarCliente();
                    break;
                case "2":
                    cadastrarProduto();
                    break;
                case "3":
                    cadastrarVenda();
                    break;
                case "4":
                    consultarViews();
                    break;
                case "5":
                    executarProcedures();
                    break;
                case "6":
                    System.out.println("Tem certeza que deseja deletar o banco de dados? (S/N)");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("S")) {
                        CreateBD.delete();
                        rodando = false;
                    } else {
                        System.out.println("Operação cancelada.");
                    }
                    break;
                case "0":
                    rodando = false;
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
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
            String sql = String.format(
                    "INSERT INTO venda (endereco_destino, valor_cobrado, data_hora, id_vendedor, id_cliente, id_trans) " +
                            "VALUES ('%s', %s, NOW(), %d, %d, %d)",
                    endereco, String.format(Locale.US, "%.2f", valor), idVendedor, idCliente, idTrans
            );
            stmt.executeUpdate(sql);
            System.out.println("Venda cadastrada com sucesso!");
        }
    }

    private void consultarViews() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            System.out.println("=== Produtos mais vendidos ===");
            var rs = stmt.executeQuery("SELECT * FROM view_produtos_mais_vendidos");
            while(rs.next()){
                System.out.println("Produto: " + rs.getString("produto") +
                        ", Quantidade vendida: " + rs.getInt("quantidade_vendida") +
                        ", Vendedor: " + rs.getString("vendedor"));
            }

            System.out.println("\n=== Total de vendas por cliente ===");
            rs = stmt.executeQuery("SELECT * FROM view_vendas_por_cliente");
            while(rs.next()){
                System.out.println("Cliente: " + rs.getString("cliente") +
                        ", Total gasto: " + rs.getBigDecimal("total_gasto"));
            }

            System.out.println("\n=== Total de vendas por transportadora ===");
            rs = stmt.executeQuery("SELECT * FROM view_vendas_por_transportadora");
            while(rs.next()){
                System.out.println("Transportadora: " + rs.getString("transportadora") +
                        ", Total vendas: " + rs.getInt("total_vendas") +
                        ", Valor total: " + rs.getBigDecimal("valor_total"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar views: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void executarProcedures() throws SQLException {
        Scanner scan = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Menu de Procedures ===");
            System.out.println("1 - Reajuste salarial");
            System.out.println("2 - Sorteio de cliente");
            System.out.println("3 - Venda (reduz estoque)");
            System.out.println("4 - Estatísticas");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scan.nextLine());

            try (Statement stmt = connection.createStatement()) {
                switch (opcao) {
                    case 1:
                        System.out.print("Informe o percentual de reajuste: ");
                        double percentual = Double.parseDouble(scan.nextLine());
                        System.out.print("Informe a categoria (PF ou PJ): ");
                        String categoria = scan.nextLine();
                        stmt.execute(String.format("CALL Reajuste(%.2f, '%s')", percentual, categoria));
                        System.out.println("Reajuste aplicado!");
                        break;

                    case 2:
                        stmt.execute("CALL Sorteio()");
                        System.out.println("Sorteio executado!");
                        break;

                    case 3:
                        System.out.print("Informe o ID do produto: ");
                        long idProduto = Long.parseLong(scan.nextLine());
                        System.out.print("Informe a quantidade: ");
                        int qtd = Integer.parseInt(scan.nextLine());
                        stmt.execute(String.format("CALL Venda(%d, %d)", idProduto, qtd));
                        System.out.println("Venda executada e estoque atualizado!");
                        break;

                    case 4:
                        var rs = stmt.executeQuery("CALL Estatisticas()");
                        System.out.println("=== Estatísticas ===");
                        while (rs.next()) {
                            System.out.println("Produto: " + rs.getString("Produto") +
                                    ", Qtde vendida: " + rs.getInt("Qtde_vendida") +
                                    ", Vendedor: " + rs.getString("Vendedor"));
                        }
                        break;

                    case 0:
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao executar procedure: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
