package database;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateUser {
    public static void create(Statement stmt) throws SQLException {

        // Criar usuário administrador
        stmt.executeUpdate("CREATE USER IF NOT EXISTS 'administrador'@'localhost' IDENTIFIED BY 'admin123'");
        stmt.executeUpdate("GRANT ALL PRIVILEGES ON *.* TO 'administrador'@'localhost' WITH GRANT OPTION");

        // Criar usuário gerente
        stmt.executeUpdate("CREATE USER IF NOT EXISTS 'gerente'@'localhost' IDENTIFIED BY 'gerente123'");
        stmt.executeUpdate("GRANT SELECT, INSERT, UPDATE, DELETE ON bancosistemaecommerce.* TO 'gerente'@'localhost'");

        // Criar usuário funcionario
        stmt.executeUpdate("CREATE USER IF NOT EXISTS 'funcionario'@'localhost' IDENTIFIED BY 'func123'");
        stmt.executeUpdate("GRANT SELECT, INSERT ON bancosistemaecommerce.* TO 'funcionario'@'localhost'");

        // Aplicar os privilégios
        stmt.executeUpdate("FLUSH PRIVILEGES");
        System.out.println("Criado usuários admin");
    }
}