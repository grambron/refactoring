package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.html.HtmlFormatter;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class QueryServletTest extends BaseTest {
    private final QueryServlet servlet = new QueryServlet(new ProductDatabase(), new HtmlFormatter());

    @Test
    public void emptyIncorrectCommandTest() {
        testCommand("avg", "Unknown command: avg");
    }

    @Test
    public void emptyMaxCommandTest() {
        formQuery("max", "<h1>Product with max price: </h1>", "");
    }

    @Test
    public void maxCommandTest() throws SQLException {
        insert(Arrays.asList(new Product("bed", 100),
                new Product("flat", 200),
                new Product("cap", 5)));
        formQuery("max", "<h1>Product with max price: </h1>", "flat\t200</br>\n");

    }

    @Test
    public void emptyMinCommandTest() {
        formQuery("min", "<h1>Product with min price: </h1>", "");
    }

    @Test
    public void minCommandTest() throws SQLException {
        insert(Arrays.asList(new Product("bed", 1),
                new Product("flat", 2),
                new Product("cup", 1)));
        formQuery("min", "<h1>Product with min price: </h1>", "bed\t1</br>\n");
    }

    @Test
    public void emptySumCommandTest() {
        formQuery("sum", "Summary price: ", "0\n");
    }

    @Test
    public void sumCommandTest() throws SQLException {
        insert(Arrays.asList(new Product("bed", 1), new Product("flat", 2)));
        formQuery("sum", "Summary price: ", "3\n");
    }

    @Test
    public void emptyCountCommandTest() {
        formQuery("count", "Number of products: ", "0\n");
    }

    @Test
    public void countCommandTest() throws SQLException {
        insert(Arrays.asList(new Product("bed", 1),
                new Product("flat", 2),
                new Product("cup", 3)));
        formQuery("count", "Number of products: ", "3\n");
    }

    private void testCommand(String command, String result) {
        when(request.getParameter("command")).thenReturn(command);
        servlet.doGet(request, response);
        assertEquals(getWritten().trim(), result.trim());
    }

    private void formQuery(String command, String commandHeader, String result) {
        String query = "<html><body>\n" +
                commandHeader + "\n" +
                result +
                "</body></html>\r\n";
        testCommand(command, query);
    }


}