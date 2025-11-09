package database;

import java.sql.SQLException;
import java.sql.Statement;

class CreateTables {
    public static void create(Statement stmt) throws SQLException {

        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS clientes (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "nome VARCHAR(255) NOT NULL, " +
                        "sexo ENUM('F', 'M', 'O'), " +
                        "idade int, " +
                        "dataNasc DATETIME" +
                        ")"
        );

        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS vendedor (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                        "nome VARCHAR(255) NOT NULL," +
                        "causa_social VARCHAR(255)," +
                        "tipo VARCHAR(255)," +
                        "nota_media DECIMAL(3, 2)" +
                        ")"
        );

        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS transportadora (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "nome VARCHAR(255) NOT NULL, " +
                        "cidade VARCHAR(20) NOT NULL" +
                        ")"
        );

        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS produtos (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "nome VARCHAR(100) NOT NULL, " +
                        "quantidade INT NOT NULL, " +
                        "valor DECIMAL(10, 2) NOT NULL, " +
                        "observacoes VARCHAR(255)" +
                        ")"
        );

        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS venda (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "endereco_destino VARCHAR(200) NOT NULL, " +
                        "valor_cobrado DECIMAL(10, 2) NOT NULL, " +
                        "data_hora DATETIME, " +
                        "id_vendedor BIGINT, " +
                        "id_cliente BIGINT, " +
                        "id_trans BIGINT, " +
                        "FOREIGN KEY (id_vendedor) REFERENCES vendedor(id), " +
                        "FOREIGN KEY (id_cliente) REFERENCES clientes(id), " +
                        "FOREIGN KEY (id_trans) REFERENCES transportadora(id)" +
                        ")"
        );

        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS vendas_produto (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "id_produto BIGINT, " +
                        "id_venda BIGINT, " +
                        "FOREIGN KEY (id_produto) REFERENCES produtos(id), " +
                        "FOREIGN KEY (id_venda) REFERENCES venda(id)" +
                        ")"
        );

        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS funcionario_especial (" +
                        "id_vendedor BIGINT PRIMARY KEY, " +
                        "bonus DECIMAL(10, 2) DEFAULT 0.00, " +
                        "FOREIGN KEY (id_vendedor) REFERENCES vendedor(id)" +
                        ")"
        );

        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS cliente_especial (" +
                        "id_cliente BIGINT PRIMARY KEY, " +
                        "cashback DECIMAL(5, 2) DEFAULT 0.00, " +
                        "FOREIGN KEY (id_cliente) REFERENCES clientes(id)" +
                        ")"
        );

        inserirDados(stmt);
    }


    private static void inserirDados(Statement stmt) throws SQLException {
        System.out.println("Inserindo dados nas tabelas...");

        System.out.println("Inserindo clientes...");
        for(int i = 0; i < 100; i++){
            String nome = "cliente" + i;
            int idade = 18 + (i % 50);
            String sexo = (i % 2 == 0) ? "M" : "F";
            String dataNasc = "200" + (i % 10) + "-01-01 00:00:00";

            String sql = String.format(
                    "INSERT INTO clientes (nome, idade, sexo, dataNasc) VALUES ('%s', %d, '%s', '%s')",
                    nome, idade, sexo, dataNasc
            );
            stmt.executeUpdate(sql);
        }

        System.out.println("Inserindo vendedores...");
        for(int i = 0; i < 5; i++){
            String nome = "vendedor" + i;
            String causa_social = "Causa Social " + i;
            String tipo = (i % 2 == 0) ? "PF" : "PJ";
            double nota_media = 4.0 + (i * 0.1);

            String sql = "INSERT INTO vendedor (nome, causa_social, tipo, nota_media) VALUES (" +
                    "'" + nome + "', " +
                    "'" + causa_social + "', " +
                    "'" + tipo + "', " +
                    nota_media + ")";
            stmt.executeUpdate(sql);
        }

        System.out.println("Inserindo transportadoras...");
        for(int i = 0; i < 3; i++){
            String nome = "transportadora" + i;
            String cidade = "Cidade" + i;

            String sql = "INSERT INTO transportadora (nome, cidade) VALUES (" +
                    "'" + nome + "', " +
                    "'" + cidade + "')";
            stmt.executeUpdate(sql);
        }

        System.out.println("Inserindo produtos...");
        for(int i = 0; i < 20; i++){
            String nome = "produto" + i;
            int quantidade = 1 + (i % 10);
            double valor = 10.0 + (i * 2);
            String observacoes = "Observações do produto " + i;

            String sql = "INSERT INTO produtos (nome, quantidade, valor, observacoes) VALUES (" +
                    "'" + nome + "', " +
                    quantidade + ", " +
                    valor + ", " +
                    "'" + observacoes + "')";
            stmt.executeUpdate(sql);
        }

        System.out.println("Inserindo vendas...");
        for(int i = 0; i < 5; i++){
            String endereco_destino = "Endereço " + i;
            double valor_cobrado = 50.0 + (i * 10);
            String data_hora = "2024-01-" + String.format("%02d", (i % 28) + 1) + " 10:00:00";
            long id_vendedor = (i % 5) + 1;
            long id_cliente = (i % 10) + 1;
            long id_trans = (i % 3) + 1;

            String sql = "INSERT INTO venda (endereco_destino, valor_cobrado, data_hora, id_vendedor, id_cliente, id_trans) VALUES (" +
                    "'" + endereco_destino + "', " +
                    valor_cobrado + ", " +
                    "'" + data_hora + "', " +
                    id_vendedor + ", " +
                    id_cliente + ", " +
                    id_trans + ")";
            stmt.executeUpdate(sql);
        }

        System.out.println("Inserindo vendas_produto...");
        for(int i = 0; i < 10; i++){
            long id_produto = (i % 5) + 1;
            long id_venda = (i % 5) + 1;

            String sql = "INSERT INTO vendas_produto (id_produto, id_venda) VALUES (" +
                    id_produto + ", " +
                    id_venda + ")";
            stmt.executeUpdate(sql);
        }

        System.out.println("Inserindo funcionários especiais...");
        for(int i = 1; i <= 2; i++){
            double bonus = 100.0 * i;

            String sql = "INSERT INTO funcionario_especial (id_vendedor, bonus) VALUES (" +
                    i + ", " +
                    bonus + ")";
            stmt.executeUpdate(sql);
        }

        System.out.println("Inserindo clientes especiais...");
        for(int i = 1; i <= 3; i++){
            double cashback = 5.0 * i;

            String sql = "INSERT INTO cliente_especial (id_cliente, cashback) VALUES (" +
                    i + ", " +
                    cashback + ")";
            stmt.executeUpdate(sql);
        }

        System.out.println("Dados inseridos com sucesso!");
    }
}