package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseUtils {
    private final String url = System.getProperty("db.url");
    private final String user = System.getProperty("db.user");
    private final String password = System.getProperty("db.password");
    private final Connection connection;
    private final QueryRunner runner;

    public DataBaseUtils() {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            this.runner = new QueryRunner();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database!", e);
        }
    }

    private <T> T executeQuery(String query, Class<T> resultType) {
        try {
            return runner.query(connection, query, new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query!", e);
        }
    }

    public String getOrderCount() {
        return executeQuery("SELECT COUNT(*) FROM order_entity;", Long.class).toString();
    }

    public String getPaymentStatus() {
        return executeQuery("SELECT status FROM payment_entity;", String.class);
    }

    public String getCreditStatus() {
        return executeQuery("SELECT status FROM credit_request_entity;", String.class);
    }

    public void clearDB() {
        try (val conn = DriverManager.getConnection(url, user, password)) {
            runner.update(conn, "DELETE FROM credit_request_entity;");
            runner.update(conn, "DELETE FROM order_entity;");
            runner.update(conn, "DELETE FROM payment_entity;");
        } catch (Exception e) {
            System.out.println("SQL exception in clearDB");
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the database connection!");
        }
    }
}
