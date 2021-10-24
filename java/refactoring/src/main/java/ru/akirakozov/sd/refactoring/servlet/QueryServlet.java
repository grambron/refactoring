package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.html.HtmlFormatter;

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

    private final ProductDatabase productDatabase;

    private final HtmlFormatter formatter;

    public QueryServlet(ProductDatabase productDatabase, HtmlFormatter formatter) {
        this.productDatabase = productDatabase;
        this.formatter = formatter;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {

            String label = "Product with max price: ";
            productDatabase.maxByPrice().ifPresentOrElse(product -> {
                formatter.setHtml(response, formatter.productToView(label, product));
            }, () -> formatter.setHtml(response, formatter.emptyProductView(label)));

        } else if ("min".equals(command)) {

            String label = "Product with min price: ";
            productDatabase.minByPrice().ifPresentOrElse(product -> {
                formatter.setHtml(response, formatter.productToView(label, product));
            }, () -> formatter.setHtml(response, formatter.emptyProductView(label)));

        } else if ("sum".equals(command)) {

            String html = formatter.dataView("Summary price: ", productDatabase.sum());
            formatter.setHtml(response, html);

        } else if ("count".equals(command)) {

            String html = formatter.dataView("Number of products: ", productDatabase.count());
            formatter.setHtml(response, html);

        } else {
            formatter.setHtml(response, "Unknown command: " + command);
        }

    }

}
