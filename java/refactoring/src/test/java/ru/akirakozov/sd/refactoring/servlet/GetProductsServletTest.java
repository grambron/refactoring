package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.entity.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProductsServletTest extends BaseTest {

    private final GetProductsServlet servlet = new GetProductsServlet();

    @Test
    public void emptyTest() throws IOException {
        servlet.doGet(request, response);
        String expected = "<html><body>\r\n" +
                "</body></html>\r\n";

        assertEquals(expected, getWritten());
    }

    @Test
    public void oneElementTest() throws SQLException, IOException {
        insert(Collections.singletonList(new Product("bed", 800)));

        servlet.doGet(request, response);
        String expected = "<html><body>\r\n" +
                "bed\t800</br>\r\n" +
                "</body></html>\r\n";

        assertEquals(expected, getWritten());
    }

    @Test
    public void manyElementsTest() throws IOException, SQLException {
        insert(Arrays.asList(new Product("bed", 800)
                , new Product("flat", 10_000)));

        servlet.doGet(request, response);
        String expected = "<html><body>\r\n" +
                "bed\t800</br>\r\n" +
                "flat\t10000</br>\r\n" +
                "</body></html>\r\n";

        assertEquals(expected, getWritten());
    }
}
