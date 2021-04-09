package util;

import lombok.extern.log4j.Log4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


@Log4j
public class DatabaseConnection {
    private static DatabaseConnection dbInstance;
    private static Connection connection;

    private DatabaseConnection() {
        // private constructor //
    }

    public static DatabaseConnection getInstance() {
        if (dbInstance == null) {
            dbInstance = new DatabaseConnection();
        }
        return dbInstance;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream("src/main/resources/dbProperties.properties"));

                String user = properties.getProperty("UserName");
                String password = properties.getProperty("Password");
                String dbUrl = properties.getProperty("DatabaseUrl");

                if (connection == null) {
                    log.info("Connection to database...");
                    connection = DriverManager.getConnection(dbUrl, user, password);
                    log.info("Connection successful!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void close() {
        if (connection != null) {
            try {
                log.info("Closing database connection");
                connection.close();
                log.info("Database connection closed");
            } catch (SQLException e) {
                log.error("Unable to close connection", e);
            }
            connection = null;
        }
    }
}