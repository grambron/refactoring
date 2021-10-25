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

    private final ProductDatabase productDatabase;

    private final HtmlFormatter formatter;

    public GetProductsServlet(ProductDatabase productDatabase, HtmlFormatter formatter) {
        this.productDatabase = productDatabase;
        this.formatter = formatter;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        formatter.setHtml(response, formatter.productsToView(productDatabase.getAll()));
    }
}
