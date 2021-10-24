package ru.akirakozov.sd.refactoring.database;

import java.sql.*;

public abstract class Database {
    private final String urlConnection;

    public Database() {
        this("jdbc:sqlite:test.db");
    }

    public Database(String urlConnection) {
        this.urlConnection = urlConnection;
    }

    public <T> T executeQuery(String sql, ResultSetResolver<T> resolver) throws SQLException {
        try (Connection connection = DriverManager.getConnection(urlConnection)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resolver.resolve(resultSet);
        }
    }

    public void executeCommand(String sql) throws SQLException {
        try (Connection connection = DriverManager.getConnection(urlConnection)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            statement.close();
        }
    }
}
