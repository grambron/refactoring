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
public class QueryServlet extends HttpServlet {

    private final ProductDatabase productDatabase = new ProductDatabase();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("<h1>Product with max price: </h1>");

            productDatabase.maxByPrice().ifPresent(product -> {
                writer.println(product.getName() + "\t" + product.getPrice() + "</br>");
            });

            writer.println("</body></html>");

        } else if ("min".equals(command)) {
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("<h1>Product with min price: </h1>");

            productDatabase.minByPrice().ifPresent(product -> {
                writer.println(product.getName() + "\t" + product.getPrice() + "</br>");
            });

            writer.println("</body></html>");
        } else if ("sum".equals(command)) {
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("Summary price: ");
            writer.println(productDatabase.sum());
            writer.println("</body></html>");
        } else if ("count".equals(command)) {
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("Number of products: ");
            writer.println(productDatabase.count());
            writer.println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
