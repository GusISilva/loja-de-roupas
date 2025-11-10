package database;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateProcedures {
    public static void create(Statement stmt) throws SQLException {

        // Reajuste salarial
        stmt.executeUpdate("DROP PROCEDURE IF EXISTS Reajuste");
        stmt.executeUpdate(
                "CREATE PROCEDURE Reajuste(IN p_percentual DECIMAL(5,2), IN p_categoria VARCHAR(50)) " +
                        "BEGIN " +
                        "   UPDATE vendedor SET nota_media = nota_media * (1 + p_percentual/100) WHERE tipo = p_categoria; " +
                        "END"
        );

        // Sorteio de cliente
        stmt.executeUpdate("DROP PROCEDURE IF EXISTS Sorteio");
        stmt.executeUpdate(
                "CREATE PROCEDURE Sorteio() " +
                        "BEGIN " +
                        "   DECLARE clienteSorteado BIGINT; " +
                        "   SELECT id_cliente INTO clienteSorteado FROM clientes ORDER BY RAND() LIMIT 1; " +
                        "   IF EXISTS(SELECT 1 FROM cliente_especial WHERE id_cliente = clienteSorteado) THEN " +
                        "       UPDATE cliente_especial SET cashback = cashback + 200 WHERE id_cliente = clienteSorteado; " +
                        "   ELSE " +
                        "       INSERT INTO cliente_especial (id_cliente, cashback) VALUES(clienteSorteado, 100); " +
                        "   END IF; " +
                        "END"
        );

        // Venda (reduz estoque automaticamente)
        stmt.executeUpdate("DROP PROCEDURE IF EXISTS Venda");
        stmt.executeUpdate(
                "CREATE PROCEDURE Venda(IN p_id_produto BIGINT, IN p_qtd INT) " +
                        "BEGIN " +
                        "   UPDATE produtos SET quantidade = quantidade - p_qtd WHERE id = p_id_produto; " +
                        "END"
        );

        // Estatísticas
        stmt.executeUpdate("DROP PROCEDURE IF EXISTS Estatisticas");
        stmt.executeUpdate(
                "CREATE PROCEDURE Estatisticas() " +
                        "BEGIN " +
                        "   SELECT p.nome AS Produto, COUNT(vp.id_venda) AS Qtde_vendida, v.nome AS Vendedor " +
                        "   FROM produtos p " +
                        "   JOIN vendas_produto vp ON p.id = vp.id_produto " +
                        "   JOIN venda vd ON vp.id_venda = vd.id " +
                        "   JOIN vendedor v ON vd.id_vendedor = v.id " +
                        "   GROUP BY p.id, v.id; " +
                        "END"
        );

        System.out.println("Criadas procedures");
    }
}
