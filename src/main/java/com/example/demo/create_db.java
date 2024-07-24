package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class create_db {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:mydatabase.db";
        try {
            Connection connection = DriverManager.getConnection(dbUrl);
            System.out.println("Base de dados criada");
            connection.close();
        } catch (SQLException e) {
            System.out.println("Erro ao criar base: " + e.getMessage());
        }
    }
}
