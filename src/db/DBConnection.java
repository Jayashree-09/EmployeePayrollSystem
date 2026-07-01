package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        try {

            String url = "jdbc:mysql://localhost:3306/payroll_system";
            String username = "root";

            // Replace with your MySQL password
            String password = "root123";

            Connection con =
                    DriverManager.getConnection(
                            url,
                            username,
                            password);

            System.out.println("Database Connected Successfully");

            return con;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
}