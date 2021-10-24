package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.akirakozov.sd.refactoring.config.Constants.JDBC_URL;


public class BaseTest {

    @Mock
    protected HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    @Mock
    protected HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    private StringWriter stringWriter;

    @BeforeAll
    protected static void createDataBase() throws SQLException {
        execute("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    @BeforeEach
    public void setUpMocks() throws IOException {
        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);
    }

    @AfterEach
    protected void cleanTable() throws SQLException {
        execute("DELETE from PRODUCT");
    }

    protected void insert(List<Product> products) throws SQLException {
        try (Connection c = DriverManager.getConnection(JDBC_URL)) {
            for (Product product : products) {
                Statement statement = c.createStatement();
                statement.executeUpdate("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")");
                statement.close();
            }
        }
    }

    protected List<Product> selectAllRows() throws SQLException {
        List<Product> result = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(JDBC_URL)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                result.add(new Product(name, price));
            }

            rs.close();
            stmt.close();
        }
        return result;
    }

    protected static void execute(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(JDBC_URL)) {
            Statement statement = c.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }
    }

    protected String getWritten() {
        return stringWriter.toString();
    }

}
