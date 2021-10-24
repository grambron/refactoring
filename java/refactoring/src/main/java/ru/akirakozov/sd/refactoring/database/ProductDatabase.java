package ru.akirakozov.sd.refactoring.database;

import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.exception.DataBaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDatabase extends Database {

    public void persist(Product product) {
        try {
            executeCommand("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")");
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public Optional<Product> maxByPrice() {
        try {
            return executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", this::mapToProduct);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public Optional<Product> minByPrice() {
        try {
            return executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", this::mapToProduct);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public long count() {
        try {
            return executeQuery("SELECT COUNT(*) as COUNT FROM PRODUCT", rs -> {
                rs.next();
                return rs.getLong("COUNT");
            });
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public long sum() {
        try {
            return executeQuery("SELECT SUM(price) as SUM FROM PRODUCT", rs -> {
                rs.next();
                return rs.getLong("SUM");
            });
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public List<Product> getAll() {
        try {
            return executeQuery("SELECT * FROM PRODUCT", this::mapProducts);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    private Optional<Product> mapToProduct(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            long price = resultSet.getLong("price");
            return Optional.of(new Product(name, price));
        } else {
            return Optional.empty();
        }
    }

    private List<Product> mapProducts(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            long price = resultSet.getLong("price");
            products.add(new Product(name, price));
        }
        return products;
    }

}
