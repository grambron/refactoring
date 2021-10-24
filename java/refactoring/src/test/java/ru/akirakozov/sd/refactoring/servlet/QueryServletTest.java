package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.entity.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class QueryServletTest extends BaseTest {
    private final QueryServlet servlet = new QueryServlet();

    @Test
    public void emptyIncorrectCommandTest() throws IOException {
        testCommand("avg", "Unknown command: avg");
    }

    @Test
    public void emptyMaxCommandTest() throws IOException {
        formQuery("max", "<h1>Product with max price: </h1>", "");
    }

    @Test
    public void maxCommandTest() throws IOException, SQLException {
        insert(Arrays.asList(new Product("bed", 100),
                new Product("flat", 200),
                new Product("cap", 5)));
        formQuery("max", "<h1>Product with max price: </h1>", "flat\t200</br>\r\n");

    }

    @Test
    public void emptyMinCommandTest() throws IOException {
        formQuery("min", "<h1>Product with min price: </h1>", "");
    }

    @Test
    public void minCommandTest() throws IOException, SQLException {
        insert(Arrays.asList(new Product("bed", 1),
                new Product("flat", 2),
                new Product("cup", 1)));
        formQuery("min", "<h1>Product with min price: </h1>", "bed\t1</br>\r\n");
    }

    @Test
    public void emptySumCommandTest() throws IOException {
        formQuery("sum", "Summary price: ", "0\r\n");
    }

    @Test
    public void sumCommandTest() throws IOException, SQLException {
        insert(Arrays.asList(new Product("bed", 1), new Product("flat", 2)));
        formQuery("sum", "Summary price: ", "3\r\n");
    }

    @Test
    public void emptyCountCommandTest() throws IOException {
        formQuery("count", "Number of products: ", "0\r\n");
    }

    @Test
    public void countCommandTest() throws IOException, SQLException {
        insert(Arrays.asList(new Product("bed", 1),
                new Product("flat", 2),
                new Product("cup", 3)));
        formQuery("count", "Number of products: ", "3\r\n");
    }

    private void testCommand(String command, String result) throws IOException {
        when(request.getParameter("command")).thenReturn(command);
        servlet.doGet(request, response);
        assertEquals(getWritten().trim(), result.trim());
    }

    private void formQuery(String command, String commandHeader, String result) throws IOException {
        String query = "<html><body>\r\n" +
                commandHeader + "\r\n" +
                result +
                "</body></html>\r\n";
        testCommand(command, query);
    }

    private void maxTest(String result) throws IOException {
        testCommand("max", "<html><body>\r\n" +
                "<h1>Product with max price: </h1>\r\n" +
                result +
                "</body></html>\r\n");
    }

    private void minTest(String result) throws IOException {
        testCommand("min", "<html><body>\r\n" +
                "<h1>Product with min price: </h1>\r\n" +
                result +
                "</body></html>\r\n");
    }

    private void countTest(String result) throws IOException {
        testCommand("count", "<html><body>\r\n" +
                "Number of products: \r\n" +
                result +
                "</body></html>\r\n");
    }

    private void sumTest(String result) throws IOException {
        testCommand("sum", "<html><body>\r\n" +
                "Summary price: \r\n" +
                result +
                "</body></html>\r\n");
    }


}