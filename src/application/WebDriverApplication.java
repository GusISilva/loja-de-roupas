package application;


import database.ConnectBD;
import database.CreateBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class WebDriverApplication {

    private final Scanner scan;

    public WebDriverApplication() throws SQLException {
        this.scan = new Scanner(System.in);
    }

    public void run() throws SQLException {
        Connection connection = ConnectBD.getConnection();
        ApplicationRunner menu = new ApplicationRunner(connection, scan);

        menu.start();

        scan.close();
        connection.close();
    }
}