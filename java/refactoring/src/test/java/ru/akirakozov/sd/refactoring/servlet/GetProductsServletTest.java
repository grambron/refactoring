package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.html.HtmlFormatter;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProductsServletTest extends BaseTest {

    private final GetProductsServlet servlet = new GetProductsServlet(new ProductDatabase(), new HtmlFormatter());

    @Test
    public void emptyTest() {
        servlet.doGet(request, response);
        String expected = "<html><body>\n" +
                "</body></html>\r\n";

        assertEquals(expected, getWritten());
    }

    @Test
    public void oneElementTest() throws SQLException {
        insert(Collections.singletonList(new Product("bed", 800)));

        servlet.doGet(request, response);
        String expected = "<html><body>\n" +
                "bed\t800</br>\n" +
                "</body></html>\r\n";

        assertEquals(expected, getWritten());
    }

    @Test
    public void manyElementsTest() throws SQLException {
        insert(Arrays.asList(new Product("bed", 800)
                , new Product("flat", 10_000)));

        servlet.doGet(request, response);
        String expected = "<html><body>\n" +
                "bed\t800</br>\n" +
                "flat\t10000</br>\n" +
                "</body></html>\r\n";

        assertEquals(expected, getWritten());
    }
}
