package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductDatabase;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final ProductDatabase productDatabase = new ProductDatabase();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter responseWriter = response.getWriter();
        responseWriter.println("<html><body>");

        productDatabase.getAll().forEach(product -> {
            responseWriter.println(product.getName() + "\t" + product.getPrice() + "</br>");
        });
        responseWriter.println("</body></html>");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
