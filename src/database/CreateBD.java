package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateBD {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASS = "root123456";
    private static final String DB_NAME = "loja_de_roupas";

    public static void create() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            stmt.executeUpdate("USE " + DB_NAME);

            CreateTables.create(stmt);
            CreateFunction.create(stmt);
            CreateTriggers.create(stmt);

            System.out.println("Banco de dados criado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DROP DATABASE " + DB_NAME);

            System.out.println("Banco de dados DELETADO com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}