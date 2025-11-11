package database;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateProcedures {
    public static void create(Statement stmt) throws SQLException {

        // Reajuste salarial
        stmt.executeUpdate("DROP PROCEDURE IF EXISTS Reajuste");
        stmt.executeUpdate(
                "CREATE PROCEDURE Reajuste(IN p_percentual DECIMAL(5,2), IN p_cargo VARCHAR(50)) " +
                        "BEGIN " +
                        "   UPDATE funcionario " +
                        "   SET salario = salario * (1 + p_percentual / 100) " +
                        "   WHERE cargo = p_cargo; " +
                        "END"
        );


        stmt.executeUpdate("DROP PROCEDURE IF EXISTS Sorteio");
        stmt.executeUpdate(
                "CREATE PROCEDURE Sorteio() " +
                        "BEGIN " +
                        "   DECLARE clienteSorteado BIGINT; " +
                        "   DECLARE tipoCliente VARCHAR(20); " +
                        "   DECLARE valorVoucher DECIMAL(10,2); " +
                        "   " +
                        "   SELECT id INTO clienteSorteado FROM clientes ORDER BY RAND() LIMIT 1; " +
                        "   " +
                        "   IF EXISTS(SELECT 1 FROM cliente_especial WHERE id_cliente = clienteSorteado) THEN " +
                        "       UPDATE cliente_especial SET cashback = cashback + 200 WHERE id_cliente = clienteSorteado; " +
                        "       SET tipoCliente = 'Cliente Especial'; " +
                        "       SET valorVoucher = 200; " +
                        "   ELSE " +
                        "       INSERT INTO cliente_especial (id_cliente, cashback) VALUES(clienteSorteado, 100); " +
                        "       SET tipoCliente = 'Cliente Comum'; " +
                        "       SET valorVoucher = 100; " +
                        "   END IF; " +
                        "   " +
                        "   SELECT clienteSorteado AS id, tipoCliente AS tipo, valorVoucher AS voucher; " +
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
                        "   DECLARE id_produto_mais_vendido BIGINT; " +
                        "   DECLARE id_produto_menos_vendido BIGINT; " +

                        "   SELECT p.id INTO id_produto_mais_vendido " +
                        "   FROM produtos p " +
                        "   JOIN vendas_produto vp ON p.id = vp.id_produto " +
                        "   GROUP BY p.id " +
                        "   ORDER BY COUNT(vp.id_venda) DESC LIMIT 1; " +

                        "   SELECT p.id INTO id_produto_menos_vendido " +
                        "   FROM produtos p " +
                        "   JOIN vendas_produto vp ON p.id = vp.id_produto " +
                        "   GROUP BY p.id " +
                        "   ORDER BY COUNT(vp.id_venda) ASC LIMIT 1; " +

                        "   SELECT 'Produto mais vendido' AS Tipo, p.nome AS Produto, v.nome AS Vendedor, " +
                        "          COUNT(vp.id_venda) AS Qtde_vendida, SUM(p.valor) AS Valor_total, " +
                        "          MONTH(MAX(vd.data_hora)) AS Mes_maior_vendas, " +
                        "          MONTH(MIN(vd.data_hora)) AS Mes_menor_vendas " +
                        "   FROM produtos p " +
                        "   JOIN vendas_produto vp ON p.id = vp.id_produto " +
                        "   JOIN venda vd ON vp.id_venda = vd.id " +
                        "   JOIN vendedor v ON vd.id_vendedor = v.id " +
                        "   WHERE p.id = id_produto_mais_vendido " +
                        "   GROUP BY p.id, v.id; " +

                        "   SELECT 'Produto menos vendido' AS Tipo, p.nome AS Produto, v.nome AS Vendedor, " +
                        "          COUNT(vp.id_venda) AS Qtde_vendida, SUM(p.valor) AS Valor_total, " +
                        "          MONTH(MAX(vd.data_hora)) AS Mes_maior_vendas, " +
                        "          MONTH(MIN(vd.data_hora)) AS Mes_menor_vendas " +
                        "   FROM produtos p " +
                        "   JOIN vendas_produto vp ON p.id = vp.id_produto " +
                        "   JOIN venda vd ON vp.id_venda = vd.id " +
                        "   JOIN vendedor v ON vd.id_vendedor = v.id " +
                        "   WHERE p.id = id_produto_menos_vendido " +
                        "   GROUP BY p.id, v.id; " +
                        "END"
        );






        System.out.println("Criadas procedures");
    }
}
