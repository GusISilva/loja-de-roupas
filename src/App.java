import java.sql.SQLException;

import application.LojaDeRoupasApplication;

public class App {
    public static void main(String[] args) {
        try {
            LojaDeRoupasApplication menu = new LojaDeRoupasApplication();
            menu.run();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
