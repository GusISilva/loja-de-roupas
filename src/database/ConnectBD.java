package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectBD {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String BANCO = "loja_de_roupas";
    private static final String USUARIO = "root";
    private static final String SENHA = "root123456";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        String urlSemBanco = URL + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        CreateBD.create();

        String urlComBanco = URL + BANCO + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        connection = DriverManager.getConnection(urlComBanco, USUARIO, SENHA);

        return connection;
    }
}