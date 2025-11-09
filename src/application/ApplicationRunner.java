package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ApplicationRunner {

    private final Scanner scanner;

    public ApplicationRunner(Connection connection, Scanner scan) throws SQLException {
        this.scanner = scan;
    }

    public void start() throws SQLException {
        System.out.println("Rodou");
        scanner.nextLine();
    }
}