package database;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateFunction {
    public static void create(Statement stmt) throws SQLException {
        try {
            stmt.executeUpdate("DROP FUNCTION IF EXISTS Calcular_idade");
            stmt.executeUpdate("DROP FUNCTION IF EXISTS Soma_fretes");
            stmt.executeUpdate("DROP FUNCTION IF EXISTS Arrecadado");
        } catch (SQLException e) {
        }

        stmt.executeUpdate(
                "CREATE FUNCTION Calcular_idade(p_id_cliente BIGINT) " +
                        "RETURNS INT " +
                        "DETERMINISTIC " +
                        "BEGIN " +
                        "   DECLARE data_atual DATE; " +
                        "   DECLARE idade INT; " +
                        "   SELECT dataNasc INTO data_atual FROM clientes WHERE id = p_id_cliente; " +
                        "   SET idade = TIMESTAMPDIFF(YEAR, data_atual, CURDATE()); " +
                        "   RETURN idade; " +
                        "END"
        );

        stmt.executeUpdate(
                "CREATE FUNCTION Soma_fretes(p_destino VARCHAR(200)) " +
                        "RETURNS DECIMAL(10,2) " +
                        "DETERMINISTIC " +
                        "READS SQL DATA " +
                        "BEGIN " +
                        "   DECLARE total_frete DECIMAL(10,2); " +
                        "   " +
                        "   SELECT COALESCE(SUM(valor_cobrado), 0) " +
                        "   INTO total_frete " +
                        "   FROM venda " +
                        "   WHERE endereco_destino = p_destino; " +
                        "   " +
                        "   RETURN total_frete; " +
                        "END"
        );

        stmt.executeUpdate(
                "CREATE FUNCTION Arrecadado(p_dia DATE, p_id_vendedor BIGINT) " +
                        "RETURNS DECIMAL(10,2) " +
                        "DETERMINISTIC " +
                        "READS SQL DATA " +
                        "BEGIN " +
                        "   DECLARE total_vendas DECIMAL(10,2); " +
                        "   " +
                        "   SELECT COALESCE(SUM(valor_cobrado), 0) " +
                        "   INTO total_vendas " +
                        "   FROM venda " +
                        "   WHERE DATE(data_hora) = p_dia AND id_vendedor = p_id_vendedor; " +
                        "   " +
                        "   RETURN total_vendas; " +
                        "END"
        );
        System.out.println("Criado functions");
    }
}