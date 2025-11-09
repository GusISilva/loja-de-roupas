package database;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateTriggers {
    public static void create(Statement stmt) throws SQLException {
        try {
            stmt.executeUpdate("DROP TRIGGER IF EXISTS Add_funcionarioEspecial");
            stmt.executeUpdate("DROP TRIGGER IF EXISTS Add_clienteEspecial");
            stmt.executeUpdate("DROP TRIGGER IF EXISTS Remove_clienteEspecial");
        } catch (SQLException e) {
        }

        stmt.executeUpdate(
                "CREATE TRIGGER Add_funcionarioEspecial " +
                        "AFTER INSERT ON venda " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "   IF NEW.valor_cobrado > 1000 THEN " +
                        "       INSERT INTO funcionario_especial (id_vendedor, bonus) " +
                        "       VALUES (NEW.id_vendedor, NEW.valor_cobrado * 0.05) " +
                        "       ON DUPLICATE KEY UPDATE " +
                        "       bonus = bonus + (NEW.valor_cobrado * 0.05); " +
                        "   END IF; " +
                        "END"
        );

        stmt.executeUpdate(
                "CREATE TRIGGER Add_clienteEspecial " +
                        "AFTER INSERT ON venda " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "   IF NEW.valor_cobrado > 500 THEN " +
                        "       INSERT INTO cliente_especial (id_cliente, cashback) " +
                        "       VALUES (NEW.id_cliente, NEW.valor_cobrado * 0.02) " +
                        "       ON DUPLICATE KEY UPDATE " +
                        "       cashback = cashback + (NEW.valor_cobrado * 0.02); " +
                        "   END IF; " +
                        "END"
        );

        stmt.executeUpdate(
                "CREATE TRIGGER Remove_clienteEspecial " +
                        "BEFORE UPDATE ON cliente_especial " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "   IF NEW.cashback <= 0 THEN " +
                        "       DELETE FROM cliente_especial WHERE id_cliente = OLD.id_cliente; " +
                        "   END IF; " +
                        "END"
        );

        System.out.println("Criado triggers");
    }
}