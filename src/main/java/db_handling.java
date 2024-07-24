import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db_handling {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
            return connection;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com a base: " + e.getMessage());
            return null;
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao interromper conexão com a base: " + e.getMessage());
        }
    }
}