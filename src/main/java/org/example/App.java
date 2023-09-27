package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/todoit",
                    "root",
                    "root123"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}