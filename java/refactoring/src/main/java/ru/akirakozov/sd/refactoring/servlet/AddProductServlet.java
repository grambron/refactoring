package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.html.HtmlFormatter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    private final ProductDatabase productDatabase;

    private final HtmlFormatter formatter;

    public AddProductServlet(ProductDatabase productDatabase, HtmlFormatter formatter) {
        this.productDatabase = productDatabase;
        this.formatter = formatter;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        productDatabase.persist(new Product(name, price));
        formatter.setHtml(response, "OK");

    }
}
