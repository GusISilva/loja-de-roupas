package database;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateViews {
    public static void create(Statement stmt) throws SQLException {

        // Produtos mais vendidos
        stmt.executeUpdate("DROP VIEW IF EXISTS view_produtos_mais_vendidos");
        stmt.executeUpdate(
                "CREATE VIEW view_produtos_mais_vendidos AS " +
                        "SELECT p.nome AS produto, COUNT(vp.id_venda) AS quantidade_vendida, v.nome AS vendedor " +
                        "FROM produtos p " +
                        "JOIN vendas_produto vp ON p.id = vp.id_produto " +
                        "JOIN venda vd ON vp.id_venda = vd.id " +
                        "JOIN vendedor v ON vd.id_vendedor = v.id " +
                        "GROUP BY p.id, v.id"
        );

        // Total de vendas por cliente
        stmt.executeUpdate("DROP VIEW IF EXISTS view_vendas_por_cliente");
        stmt.executeUpdate(
                "CREATE VIEW view_vendas_por_cliente AS " +
                        "SELECT c.nome AS cliente, SUM(v.valor_cobrado) AS total_gasto " +
                        "FROM clientes c " +
                        "JOIN venda v ON c.id = v.id_cliente " +
                        "GROUP BY c.id"
        );

        // Total de vendas por transportadora
        stmt.executeUpdate("DROP VIEW IF EXISTS view_vendas_por_transportadora");
        stmt.executeUpdate(
                "CREATE VIEW view_vendas_por_transportadora AS " +
                        "SELECT t.nome AS transportadora, COUNT(v.id) AS total_vendas, SUM(v.valor_cobrado) AS valor_total " +
                        "FROM transportadora t " +
                        "JOIN venda v ON t.id = v.id_trans " +
                        "GROUP BY t.id"
        );

        System.out.println("Criadas views");
    }
}
