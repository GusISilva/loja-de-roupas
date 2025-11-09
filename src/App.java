import java.sql.SQLException;

import application.WebDriverApplication;
import database.CreateBD;
import application.ApplicationRunner;

public class App {
    public static void main(String[] args) {
        try {
            WebDriverApplication menu = new WebDriverApplication();
            menu.run();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
