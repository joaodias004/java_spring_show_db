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


//CREATE TABLE users (
    //    id INTEGER PRIMARY KEY AUTOINCREMENT,
      //  username TEXT NOT NULL UNIQUE,
      //  password TEXT NOT NULL
//);


//SELECT * FROM "user" u

//INSERT INTO User (username, password) VALUES ('testUser1', '$2a$10$X5gO5z7DOU15dWXKT7Y9ueu82cfb/mGQiirxXS5eoNtDPjeK/68Se');
