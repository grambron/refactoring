package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.html.HtmlFormatter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final ProductDatabase productDatabase = new ProductDatabase();

    private final HtmlFormatter formatter = new HtmlFormatter();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        formatter.setHtml(response, formatter.productsToView(productDatabase.getAll()));
    }
}
