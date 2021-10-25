package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.html.HtmlFormatter;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AddProductServletTest extends BaseTest {
    private final AddProductServlet servlet = new AddProductServlet(new ProductDatabase(), new HtmlFormatter());

    @Test
    public void emptyParamsTest() {
        assertThrows(NumberFormatException.class, () -> servlet.doGet(request, response));
    }

    @Test
    public void simpleAddingTest() throws SQLException {
        when(request.getParameter("name")).thenReturn("bed");
        when(request.getParameter("price")).thenReturn("1000");
        servlet.doGet(request, response);

        assertEquals("OK\r\n", getWritten());
        List<Product> result = selectAllRows();
        assertEquals(1, result.size());
        assertEquals("bed", result.get(0).getName());
        assertEquals(1000, result.get(0).getPrice());
    }

    @Test
    void priceIsNotIntegerTest() {
        assertThrows(NumberFormatException.class, () -> {
            when(request.getParameter("name")).thenReturn("bed");
            when(request.getParameter("price")).thenReturn("abc");

            servlet.doGet(request, response);
        });
    }
}